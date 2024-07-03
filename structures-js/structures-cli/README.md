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
@kinotic/structures-cli/2.1.0 darwin-x64 node-v18.17.0
$ structures --help [COMMAND]
USAGE
  $ structures COMMAND
...
```
<!-- usagestop -->
# Commands
<!-- commands -->
* [`structures autocomplete [SHELL]`](#structures-autocomplete-shell)
* [`structures gen [NAMESPACE]`](#structures-gen-namespace)
* [`structures generate [NAMESPACE]`](#structures-generate-namespace)
* [`structures help [COMMAND]`](#structures-help-command)
* [`structures init`](#structures-init)
* [`structures initialize`](#structures-initialize)
* [`structures plugins`](#structures-plugins)
* [`structures plugins add PLUGIN`](#structures-plugins-add-plugin)
* [`structures plugins:inspect PLUGIN...`](#structures-pluginsinspect-plugin)
* [`structures plugins install PLUGIN`](#structures-plugins-install-plugin)
* [`structures plugins link PATH`](#structures-plugins-link-path)
* [`structures plugins remove [PLUGIN]`](#structures-plugins-remove-plugin)
* [`structures plugins reset`](#structures-plugins-reset)
* [`structures plugins uninstall [PLUGIN]`](#structures-plugins-uninstall-plugin)
* [`structures plugins unlink [PLUGIN]`](#structures-plugins-unlink-plugin)
* [`structures plugins update`](#structures-plugins-update)
* [`structures sync [NAMESPACE]`](#structures-sync-namespace)
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

## `structures gen [NAMESPACE]`

This will generate all Entity Service classes.

```
USAGE
  $ structures gen [NAMESPACE] [-v]

ARGUMENTS
  NAMESPACE  The namespace that you want to generate service classes for

FLAGS
  -v, --verbose  Enable verbose logging

DESCRIPTION
  This will generate all Entity Service classes.

ALIASES
  $ structures gen

EXAMPLES
  $ structures generate

  $ structures gen

  $ structures gen my.namespace -v
```

## `structures generate [NAMESPACE]`

This will generate all Entity Service classes.

```
USAGE
  $ structures generate [NAMESPACE] [-v]

ARGUMENTS
  NAMESPACE  The namespace that you want to generate service classes for

FLAGS
  -v, --verbose  Enable verbose logging

DESCRIPTION
  This will generate all Entity Service classes.

ALIASES
  $ structures gen

EXAMPLES
  $ structures generate

  $ structures gen

  $ structures gen my.namespace -v
```

_See code: [src/commands/generate.ts](https://github.com/Kinotic-Foundation/structures/blob/v2.1.0/src/commands/generate.ts)_

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

ALIASES
  $ structures init

EXAMPLES
  $ structures initialize --namespace my.namespace --entities path/to/entities --generated path/to/services

  $ structures init --namespace my.namespace --entities path/to/entities --generated path/to/services

  $ structures init -n my.namespace -e path/to/entities -g path/to/services
```

## `structures initialize`

This will initialize a new Structures Project for use with the Structures CLI.

```
USAGE
  $ structures initialize -n <value> -e <value> -g <value>

FLAGS
  -e, --entities=<value>   (required) Path to the directory containing the Entity definitions
  -g, --generated=<value>  (required) Path to the directory to write generated Services
  -n, --namespace=<value>  (required) The name of the namespace you want to use

DESCRIPTION
  This will initialize a new Structures Project for use with the Structures CLI.

ALIASES
  $ structures init

EXAMPLES
  $ structures initialize --namespace my.namespace --entities path/to/entities --generated path/to/services

  $ structures init --namespace my.namespace --entities path/to/entities --generated path/to/services

  $ structures init -n my.namespace -e path/to/entities -g path/to/services
```

_See code: [src/commands/initialize.ts](https://github.com/Kinotic-Foundation/structures/blob/v2.1.0/src/commands/initialize.ts)_

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

_See code: [@oclif/plugin-plugins](https://github.com/oclif/plugin-plugins/blob/v5.0.14/src/commands/plugins/index.ts)_

## `structures plugins add PLUGIN`

Installs a plugin into structures.

```
USAGE
  $ structures plugins add PLUGIN... [--json] [-f] [-h] [-s | -v]

ARGUMENTS
  PLUGIN...  Plugin to install.

FLAGS
  -f, --force    Force npm to fetch remote resources even if a local copy exists on disk.
  -h, --help     Show CLI help.
  -s, --silent   Silences npm output.
  -v, --verbose  Show verbose npm output.

GLOBAL FLAGS
  --json  Format output as json.

DESCRIPTION
  Installs a plugin into structures.

  Uses bundled npm executable to install plugins into /Users/navid/.local/share/structures

  Installation of a user-installed plugin will override a core plugin.

  Use the STRUCTURES_NPM_LOG_LEVEL environment variable to set the npm loglevel.
  Use the STRUCTURES_NPM_REGISTRY environment variable to set the npm registry.

ALIASES
  $ structures plugins add

EXAMPLES
  Install a plugin from npm registry.

    $ structures plugins add myplugin

  Install a plugin from a github url.

    $ structures plugins add https://github.com/someuser/someplugin

  Install a plugin from a github slug.

    $ structures plugins add someuser/someplugin
```

## `structures plugins:inspect PLUGIN...`

Displays installation properties of a plugin.

```
USAGE
  $ structures plugins inspect PLUGIN...

ARGUMENTS
  PLUGIN...  [default: .] Plugin to inspect.

FLAGS
  -h, --help     Show CLI help.
  -v, --verbose

GLOBAL FLAGS
  --json  Format output as json.

DESCRIPTION
  Displays installation properties of a plugin.

EXAMPLES
  $ structures plugins inspect myplugin
```

_See code: [@oclif/plugin-plugins](https://github.com/oclif/plugin-plugins/blob/v5.0.14/src/commands/plugins/inspect.ts)_

## `structures plugins install PLUGIN`

Installs a plugin into structures.

```
USAGE
  $ structures plugins install PLUGIN... [--json] [-f] [-h] [-s | -v]

ARGUMENTS
  PLUGIN...  Plugin to install.

FLAGS
  -f, --force    Force npm to fetch remote resources even if a local copy exists on disk.
  -h, --help     Show CLI help.
  -s, --silent   Silences npm output.
  -v, --verbose  Show verbose npm output.

GLOBAL FLAGS
  --json  Format output as json.

DESCRIPTION
  Installs a plugin into structures.

  Uses bundled npm executable to install plugins into /Users/navid/.local/share/structures

  Installation of a user-installed plugin will override a core plugin.

  Use the STRUCTURES_NPM_LOG_LEVEL environment variable to set the npm loglevel.
  Use the STRUCTURES_NPM_REGISTRY environment variable to set the npm registry.

ALIASES
  $ structures plugins add

EXAMPLES
  Install a plugin from npm registry.

    $ structures plugins install myplugin

  Install a plugin from a github url.

    $ structures plugins install https://github.com/someuser/someplugin

  Install a plugin from a github slug.

    $ structures plugins install someuser/someplugin
```

_See code: [@oclif/plugin-plugins](https://github.com/oclif/plugin-plugins/blob/v5.0.14/src/commands/plugins/install.ts)_

## `structures plugins link PATH`

Links a plugin into the CLI for development.

```
USAGE
  $ structures plugins link PATH [-h] [--install] [-v]

ARGUMENTS
  PATH  [default: .] path to plugin

FLAGS
  -h, --help          Show CLI help.
  -v, --verbose
      --[no-]install  Install dependencies after linking the plugin.

DESCRIPTION
  Links a plugin into the CLI for development.
  Installation of a linked plugin will override a user-installed or core plugin.

  e.g. If you have a user-installed or core plugin that has a 'hello' command, installing a linked plugin with a 'hello'
  command will override the user-installed or core plugin implementation. This is useful for development work.


EXAMPLES
  $ structures plugins link myplugin
```

_See code: [@oclif/plugin-plugins](https://github.com/oclif/plugin-plugins/blob/v5.0.14/src/commands/plugins/link.ts)_

## `structures plugins remove [PLUGIN]`

Removes a plugin from the CLI.

```
USAGE
  $ structures plugins remove [PLUGIN...] [-h] [-v]

ARGUMENTS
  PLUGIN...  plugin to uninstall

FLAGS
  -h, --help     Show CLI help.
  -v, --verbose

DESCRIPTION
  Removes a plugin from the CLI.

ALIASES
  $ structures plugins unlink
  $ structures plugins remove

EXAMPLES
  $ structures plugins remove myplugin
```

## `structures plugins reset`

Remove all user-installed and linked plugins.

```
USAGE
  $ structures plugins reset [--hard] [--reinstall]

FLAGS
  --hard       Delete node_modules and package manager related files in addition to uninstalling plugins.
  --reinstall  Reinstall all plugins after uninstalling.
```

_See code: [@oclif/plugin-plugins](https://github.com/oclif/plugin-plugins/blob/v5.0.14/src/commands/plugins/reset.ts)_

## `structures plugins uninstall [PLUGIN]`

Removes a plugin from the CLI.

```
USAGE
  $ structures plugins uninstall [PLUGIN...] [-h] [-v]

ARGUMENTS
  PLUGIN...  plugin to uninstall

FLAGS
  -h, --help     Show CLI help.
  -v, --verbose

DESCRIPTION
  Removes a plugin from the CLI.

ALIASES
  $ structures plugins unlink
  $ structures plugins remove

EXAMPLES
  $ structures plugins uninstall myplugin
```

_See code: [@oclif/plugin-plugins](https://github.com/oclif/plugin-plugins/blob/v5.0.14/src/commands/plugins/uninstall.ts)_

## `structures plugins unlink [PLUGIN]`

Removes a plugin from the CLI.

```
USAGE
  $ structures plugins unlink [PLUGIN...] [-h] [-v]

ARGUMENTS
  PLUGIN...  plugin to uninstall

FLAGS
  -h, --help     Show CLI help.
  -v, --verbose

DESCRIPTION
  Removes a plugin from the CLI.

ALIASES
  $ structures plugins unlink
  $ structures plugins remove

EXAMPLES
  $ structures plugins unlink myplugin
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

_See code: [@oclif/plugin-plugins](https://github.com/oclif/plugin-plugins/blob/v5.0.14/src/commands/plugins/update.ts)_

## `structures sync [NAMESPACE]`

Synchronize the local Entity definitions with the Structures Server

```
USAGE
  $ structures sync [NAMESPACE] [-s <value>] [-p] [-v] [--dryRun]

ARGUMENTS
  NAMESPACE  The namespace the Entities belong to

FLAGS
  -p, --publish         Publish each Entity after save/update
  -s, --server=<value>  The structures server to connect to
  -v, --verbose         Enable verbose logging
      --dryRun          Dry run enables verbose logging and does not save any changes to the server

DESCRIPTION
  Synchronize the local Entity definitions with the Structures Server

ALIASES
  $ structures sync

EXAMPLES
  $ structures synchronize

  $ structures sync

  $ structures synchronize my.namespace --server http://localhost:9090 --publish --verbose

  $ structures sync my.namespace -p -v -s http://localhost:9090
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
      --dryRun          Dry run enables verbose logging and does not save any changes to the server

DESCRIPTION
  Synchronize the local Entity definitions with the Structures Server

ALIASES
  $ structures sync

EXAMPLES
  $ structures synchronize

  $ structures sync

  $ structures synchronize my.namespace --server http://localhost:9090 --publish --verbose

  $ structures sync my.namespace -p -v -s http://localhost:9090
```

_See code: [src/commands/synchronize.ts](https://github.com/Kinotic-Foundation/structures/blob/v2.1.0/src/commands/synchronize.ts)_

## `structures update [CHANNEL]`

update the structures CLI

```
USAGE
  $ structures update [CHANNEL] [-a] [--force] [-i | -v <value>]

FLAGS
  -a, --available        See available versions.
  -i, --interactive      Interactively select version to install. This is ignored if a channel is provided.
  -v, --version=<value>  Install a specific version.
      --force            Force a re-download of the requested version.

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

_See code: [@oclif/plugin-update](https://github.com/oclif/plugin-update/blob/v4.2.7/src/commands/update.ts)_
<!-- commandsstop -->
