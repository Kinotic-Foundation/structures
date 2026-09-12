`StompServerOptions.setHeartbeat` in vertx-stomp-lite takes a `JsonObject` with two integer keys,
`x` and `y`, and the one caller in kinotic builds one by hand:

```java
// StompHeartbeatTests.startServer — the gateway itself runs on the library default
StompServerOptions stompServerOptions = new StompServerOptions()
        .setWebsocketPath("/v1")
        .setHeartbeat(new JsonObject().put("x", HEARTBEAT_MS).put("y", HEARTBEAT_MS));
```

A JSON object for two ints is Primitive Obsession on the library's own API: nothing checks the
key names at compile time, a `long` is silently narrowed through `getInteger`, and the reader has
to know the STOMP spec's `x,y` convention to tell which direction is which. I want the option to
be a record.

What I already know, so you don't re-derive it:

- vertx-stomp-lite is our library (`org.kinotic:vertx-stomp-lite`, pinned by
  `vertxStompLiteVersion` in `gradle.properties`, 6.0.0 today). The change is made there and
  released before the gateway picks it up; never work around an unpublished version.
- The library already has the type, nested and package-private in spirit: `Frame.Heartbeat`
  holds `final int x; final int y`, with `parse(String)` for the `heart-beat` header,
  `create(JsonObject)` for the option, `toString()` for the CONNECTED header, and the two
  `compute*HeartbeatPeriod` functions. `StompServerOptions.DEFAULT_STOMP_HEARTBEAT` is a
  `JsonObject` of `x=30000, y=30000`, and `DefaultStompServerConnection` calls
  `Heartbeat.create(options.getHeartbeat())` on every CONNECT.
- The semantics to preserve, from the STOMP 1.2 spec and the library's code: `x` is what the
  server can send, `y` what it wants to receive, both in milliseconds, `0` meaning none; the
  negotiated client period is `max(client.x, server.y)` and the server period
  `max(server.x, client.y)`, each `0` if either side offered `0`; a connection silent for more
  than twice the client period is closed through `handler.closed()`.
- The gateway offers the library default both ways, which the TS client matches;
  `StompHeartbeatTests` in `kinotic-api-gateway` shortens it on its own options and pins the
  close on a real socket. It is the only call site of `setHeartbeat`.

Do this:

1. In vertx-stomp-lite, promote the heartbeat to a top-level record, `StompHeartbeat(int x, int y)`
   or a clearer pair of names if the spec's letters are not worth keeping, in the options
   package, with `parse(String)` and the header rendering on it. `StompServerOptions`
   gets `setHeartbeat(StompHeartbeat)` and `getHeartbeat()` returning it, with the default as a
   record constant; `Frame.Heartbeat` and `create(JsonObject)` go away, and
   `DefaultStompServerConnection` and the period functions use the record. If the options class
   has a `JsonObject` constructor or `toJson()` for Vert.x-style configuration, the record maps
   to and from `{x, y}` there so a JSON-configured server keeps working. Release the library.
2. In kinotic, bump `vertxStompLiteVersion` and replace the `JsonObject` construction in
   `StompHeartbeatTests` with the record.

Validate step 2 with `dependencyInsight` on `:kinotic-api-gateway:compileClasspath` for the new
library version, `:kinotic-api-gateway:test`, and a grep for `"x"` under `kinotic-api-gateway`
to confirm no key names survive.
