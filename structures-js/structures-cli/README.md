Structures CLI
=================

<!-- toc -->
* [Usage](#usage)
* [Commands](#commands)
<!-- tocstop -->
# Usage
<!-- usage -->
```sh-session
$ npm install -g @kinotic/structures-cli
$ structures COMMAND
running command...
$ structures (--version)
@kinotic/structures-cli/1.1.1 darwin-x64 node-v20.11.0
$ structures --help [COMMAND]
USAGE
  $ structures COMMAND
...
```
<!-- usagestop -->
# Commands
<!-- commands -->
* [`structures autocomplete [SHELL]`](#structures-autocomplete-shell)
* [`structures help [COMMAND]`](#structures-help-command)
* [`structures init`](#structures-init)
* [`structures plugins`](#structures-plugins)
* [`structures plugins:install PLUGIN...`](#structures-pluginsinstall-plugin)
* [`structures plugins:inspect PLUGIN...`](#structures-pluginsinspect-plugin)
* [`structures plugins:install PLUGIN...`](#structures-pluginsinstall-plugin-1)
* [`structures plugins:link PLUGIN`](#structures-pluginslink-plugin)
* [`structures plugins:uninstall PLUGIN...`](#structures-pluginsuninstall-plugin)
* [`structures plugins:uninstall PLUGIN...`](#structures-pluginsuninstall-plugin-1)
* [`structures plugins:uninstall PLUGIN...`](#structures-pluginsuninstall-plugin-2)
* [`structures plugins update`](#structures-plugins-update)
* [`structures synchronize [NAMESPACE]`](#structures-synchronize-namespace)
* [`structures update [CHANNEL]`](#structures-update-channel)

## `structures autocomplete [SHELL]`

display autocomplete installation instructions

```
USAGE
  $ structures autocomplete [SHELL] [-r]

ARGUMENTS
  SHELL  (zsh|bash|powershell) Shell type

FLAGS
  -r, --refresh-cache  Refresh cache (ignores displaying instructions)

DESCRIPTION
  display autocomplete installation instructions

EXAMPLES
  $ structures autocomplete

  $ structures autocomplete bash

  $ structures autocomplete zsh

  $ structures autocomplete powershell

  $ structures autocomplete --refresh-cache
```

_See code: [@oclif/plugin-autocomplete](https://github.com/oclif/plugin-autocomplete/blob/v2.3.3/src/commands/autocomplete/index.ts)_

## `structures help [COMMAND]`

display help for structures

```
USAGE
  $ structures help [COMMAND] [--json] [--all]

ARGUMENTS
  COMMAND  command to show help for

FLAGS
  --all   see all commands in CLI
  --json  Format output as json.

DESCRIPTION
  display help for structures
```

_See code: [@oclif/plugin-help](https://github.com/oclif/plugin-help/blob/v5.0.0/src/commands/help.ts)_

## `structures init`

This will initialize a new Structures Project for use with the Structures CLI.

```
USAGE
  $ structures init -n <value> -e <value> -g <value>

FLAGS
  -e, --entities=<value>   (required) Path to the directory containing the Entity definitions
  -g, --generated=<value>  (required) Path to the directory to write generated Services
  -n, --namespace=<value>  (required) The name of the namespace you want to use

DESCRIPTION
  This will initialize a new Structures Project for use with the Structures CLI.

EXAMPLES
  $ structures init --namespace my.namespace --entities path/to/entities --generated path/to/services

  $ structures init -n my.namespace -e path/to/entities -g path/to/services
```

_See code: [dist/commands/init.ts](https://github.com/Kinotic-Foundation/structures/blob/v1.1.1/dist/commands/init.ts)_

## `structures plugins`

List installed plugins.

```
USAGE
  $ structures plugins [--json] [--core]

FLAGS
  --core  Show core plugins.

GLOBAL FLAGS
  --json  Format output as json.

DESCRIPTION
  List installed plugins.

EXAMPLES
  $ structures plugins
```

_See code: [@oclif/plugin-plugins](https://github.com/oclif/plugin-plugins/blob/v3.1.7/src/commands/plugins/index.ts)_

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

## `structures synchronize [NAMESPACE]`

Synchronize the local Entity definitions with the Structures Server

```
USAGE
  $ structures synchronize [NAMESPACE] [-s <value>] [-p] [-v] [--dryRun]

ARGUMENTS
  NAMESPACE  The namespace the Entities belong to

FLAGS
  -p, --publish         Publish each Entity after save/update
  -s, --server=<value>  The structures server to connect to
  -v, --verbose         Enable verbose logging
  --dryRun              Dry run enables verbose logging and does not save any changes to the server

DESCRIPTION
  Synchronize the local Entity definitions with the Structures Server

EXAMPLES
  $ structures synchronize my.namespace --server http://localhost:9090 --publish --verbose

  $ structures synchronize my.namespace -p

  $ structures synchronize
```

_See code: [dist/commands/synchronize.ts](https://github.com/Kinotic-Foundation/structures/blob/v1.1.1/dist/commands/synchronize.ts)_

## `structures update [CHANNEL]`

update the structures CLI

```
USAGE
  $ structures update [CHANNEL] [-a] [-v <value> | -i] [--force]

FLAGS
  -a, --available        Install a specific version.
  -i, --interactive      Interactively select version to install. This is ignored if a channel is provided.
  -v, --version=<value>  Install a specific version.
  --force                Force a re-download of the requested version.

DESCRIPTION
  update the structures CLI

EXAMPLES
  Update to the stable channel:

    $ structures update stable

  Update to a specific version:

    $ structures update --version 1.0.0

  Interactively select version:

    $ structures update --interactive

  See available versions:

    $ structures update --available
```

_See code: [@oclif/plugin-update](https://github.com/oclif/plugin-update/blob/v3.1.27/src/commands/update.ts)_
<!-- commandsstop -->
