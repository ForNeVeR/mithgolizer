Mithgolizer
============

Mithgolizer is a modern headless XMPP moderation solution.

Using
-----

### Configuration

Copy `mithgolizer.properties.example` file to `mithgolizer.properties` and tune
it. All options should be self-explanatory. Mithgolizer will **ban** users who
satisfies filtering criteria.

By default Mithgolizer will use the configuration file `mithgolizer.properties`
from the current directory but it will read the path to the configuration file
from the program arguments.

### Building

```console
$ sbt build
```
