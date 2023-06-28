oclif-hello-world
=================

oclif example Hello World CLI

[![oclif](https://img.shields.io/badge/cli-oclif-brightgreen.svg)](https://oclif.io)
[![CircleCI](https://circleci.com/gh/oclif/hello-world/tree/main.svg?style=shield)](https://circleci.com/gh/oclif/hello-world/tree/main)
[![GitHub license](https://img.shields.io/github/license/oclif/hello-world)](https://github.com/oclif/hello-world/blob/main/LICENSE)

<!-- toc -->
* [Usage](#usage)
* [Commands](#commands)
<!-- tocstop -->
# Usage
<!-- usage -->
```sh-session
$ npm install -g structures-cli
$ structures COMMAND
running command...
$ structures (--version)
structures-cli/0.0.0 darwin-x64 node-v18.15.0
$ structures --help [COMMAND]
USAGE
  $ structures COMMAND
...
```
<!-- usagestop -->
# Commands
<!-- commands -->
* [`structures hello PERSON`](#structures-hello-person)
* [`structures hello world`](#structures-hello-world)
* [`structures help [COMMANDS]`](#structures-help-commands)
* [`structures plugins`](#structures-plugins)
* [`structures plugins:install PLUGIN...`](#structures-pluginsinstall-plugin)
* [`structures plugins:inspect PLUGIN...`](#structures-pluginsinspect-plugin)
* [`structures plugins:install PLUGIN...`](#structures-pluginsinstall-plugin-1)
* [`structures plugins:link PLUGIN`](#structures-pluginslink-plugin)
* [`structures plugins:uninstall PLUGIN...`](#structures-pluginsuninstall-plugin)
* [`structures plugins:uninstall PLUGIN...`](#structures-pluginsuninstall-plugin-1)
* [`structures plugins:uninstall PLUGIN...`](#structures-pluginsuninstall-plugin-2)
* [`structures plugins update`](#structures-plugins-update)

## `structures hello PERSON`

Say hello

```
USAGE
  $ structures hello PERSON -f <value>

ARGUMENTS
  PERSON  Person to say hello to

FLAGS
  -f, --from=<value>  (required) Who is saying hello

DESCRIPTION
  Say hello

EXAMPLES
  $ oex hello friend --from oclif
  hello friend from oclif! (./src/commands/hello/index.ts)
```

_See code: [dist/commands/hello/index.ts](https://github.com/Kinotic-Foundation/structures/structures/blob/v0.0.0/dist/commands/hello/index.ts)_

## `structures hello world`

Say hello world

```
USAGE
  $ structures hello world

DESCRIPTION
  Say hello world

EXAMPLES
  $ structures hello world
  hello world! (./src/commands/hello/world.ts)
```

## `structures help [COMMANDS]`

Display help for structures.

```
USAGE
  $ structures help [COMMANDS] [-n]

ARGUMENTS
  COMMANDS  Command to show help for.

FLAGS
  -n, --nested-commands  Include all nested commands in the output.

DESCRIPTION
  Display help for structures.
```

_See code: [@oclif/plugin-help](https://github.com/oclif/plugin-help/blob/v5.2.10/src/commands/help.ts)_

## `structures plugins`

List installed plugins.

```
USAGE
  $ structures plugins [--core]

FLAGS
  --core  Show core plugins.

DESCRIPTION
  List installed plugins.

EXAMPLES
  $ structures plugins
```

_See code: [@oclif/plugin-plugins](https://github.com/oclif/plugin-plugins/blob/v2.4.7/src/commands/plugins/index.ts)_

## `structures plugins:install PLUGIN...`

Installs a plugin into the CLI.

```
USAGE
  $ structures plugins:install PLUGIN...

ARGUMENTS
  PLUGIN  Plugin to install.

FLAGS
  -f, --force    Run yarn install with force flag.
  -h, --help     Show CLI help.
  -v, --verbose

DESCRIPTION
  Installs a plugin into the CLI.
  Can be installed from npm or a git url.

  Installation of a user-installed plugin will override a core plugin.

  e.g. If you have a core plugin that has a 'hello' command, installing a user-installed plugin with a 'hello' command
  will override the core plugin implementation. This is useful if a user needs to update core plugin functionality in
  the CLI without the need to patch and update the whole CLI.


ALIASES
  $ structures plugins add

EXAMPLES
  $ structures plugins:install myplugin 

  $ structures plugins:install https://github.com/someuser/someplugin

  $ structures plugins:install someuser/someplugin
```

## `structures plugins:inspect PLUGIN...`

Displays installation properties of a plugin.

```
USAGE
  $ structures plugins:inspect PLUGIN...

ARGUMENTS
  PLUGIN  [default: .] Plugin to inspect.

FLAGS
  -h, --help     Show CLI help.
  -v, --verbose

GLOBAL FLAGS
  --json  Format output as json.

DESCRIPTION
  Displays installation properties of a plugin.

EXAMPLES
  $ structures plugins:inspect myplugin
```

## `structures plugins:install PLUGIN...`

Installs a plugin into the CLI.

```
USAGE
  $ structures plugins:install PLUGIN...

ARGUMENTS
  PLUGIN  Plugin to install.

FLAGS
  -f, --force    Run yarn install with force flag.
  -h, --help     Show CLI help.
  -v, --verbose

DESCRIPTION
  Installs a plugin into the CLI.
  Can be installed from npm or a git url.

  Installation of a user-installed plugin will override a core plugin.

  e.g. If you have a core plugin that has a 'hello' command, installing a user-installed plugin with a 'hello' command
  will override the core plugin implementation. This is useful if a user needs to update core plugin functionality in
  the CLI without the need to patch and update the whole CLI.


ALIASES
  $ structures plugins add

EXAMPLES
  $ structures plugins:install myplugin 

  $ structures plugins:install https://github.com/someuser/someplugin

  $ structures plugins:install someuser/someplugin
```

## `structures plugins:link PLUGIN`

Links a plugin into the CLI for development.

```
USAGE
  $ structures plugins:link PLUGIN

ARGUMENTS
  PATH  [default: .] path to plugin

FLAGS
  -h, --help     Show CLI help.
  -v, --verbose

DESCRIPTION
  Links a plugin into the CLI for development.
  Installation of a linked plugin will override a user-installed or core plugin.

  e.g. If you have a user-installed or core plugin that has a 'hello' command, installing a linked plugin with a 'hello'
  command will override the user-installed or core plugin implementation. This is useful for development work.


EXAMPLES
  $ structures plugins:link myplugin
```

## `structures plugins:uninstall PLUGIN...`

Removes a plugin from the CLI.

```
USAGE
  $ structures plugins:uninstall PLUGIN...

ARGUMENTS
  PLUGIN  plugin to uninstall

FLAGS
  -h, --help     Show CLI help.
  -v, --verbose

DESCRIPTION
  Removes a plugin from the CLI.

ALIASES
  $ structures plugins unlink
  $ structures plugins remove
```

## `structures plugins:uninstall PLUGIN...`

Removes a plugin from the CLI.

```
USAGE
  $ structures plugins:uninstall PLUGIN...

ARGUMENTS
  PLUGIN  plugin to uninstall

FLAGS
  -h, --help     Show CLI help.
  -v, --verbose

DESCRIPTION
  Removes a plugin from the CLI.

ALIASES
  $ structures plugins unlink
  $ structures plugins remove
```

## `structures plugins:uninstall PLUGIN...`

Removes a plugin from the CLI.

```
USAGE
  $ structures plugins:uninstall PLUGIN...

ARGUMENTS
  PLUGIN  plugin to uninstall

FLAGS
  -h, --help     Show CLI help.
  -v, --verbose

DESCRIPTION
  Removes a plugin from the CLI.

ALIASES
  $ structures plugins unlink
  $ structures plugins remove
```

## `structures plugins update`

Update installed plugins.

```
USAGE
  $ structures plugins update [-h] [-v]

FLAGS
  -h, --help     Show CLI help.
  -v, --verbose

DESCRIPTION
  Update installed plugins.
```
<!-- commandsstop -->
