# Web Storage effects handler for re-frame

This is a
re-frame
["Effects Handler"](https://github.com/Day8/re-frame/wiki/Effectful-Event-Handlers)
for [Web Storage](https://en.wikipedia.org/wiki/Web_storage). It is very much inspired
by <https://github.com/SMX-LTD/re-frame-cookie-fx>, to which I owe a debt of gratitude.

## Overview

The Web Storage API, originally part of HTML5, is a mechanism to store state in the
client. It is somewhat similar to cookies, but without any automated mechanisim to send
the state to a server.

Web Storage is a simple mapping of string keys to string values. Actually, it is two
such mappings: "local" and "session".

Local storage is per origin (protocol, hostname, and port) and persistent, while Session
storage is further restricted to a single window or tab.

## Usage

In your event handler (typically `handlers.cljs` or `events.cljs`), add this package to your `ns`.

```clj
(ns ...
  (:require
    ...
    [com.degel.re-frame-storage-fx]
    ...))
```

Then write event handlers that use these effects and coeffects:

TBD...

Coeffects:

- `:storage/empty?`
- `:storage/get`
- `:storage/all`
- `:storage/keys`
- `:storage/vals`
- `:storage/count`

Effects:

- `:storage/set`
- `:storage/remove`

# Canned instructions

The rest of this Readme is the original boilerplate from the Mies template. It should
all be correct, but is relevant only for maintainers of this library, not users.

## Setup

Most of the following scripts require [rlwrap](http://utopia.knoware.nl/~hlub/uck/rlwrap/) (on OS X installable via brew).

Build your project once in dev mode with the following script and then open `index.html` in your browser.

    ./scripts/build

To auto build your project in dev mode:

    ./scripts/watch

To start an auto-building Node REPL:

    ./scripts/repl

To get source map support in the Node REPL:

    lein npm install
    
To start a browser REPL:
    
1. Uncomment the following lines in src/re_frame_storage_fx/core.cljs:
```clojure
;; (defonce conn
;;   (repl/connect "http://localhost:9000/repl"))
```
2. Run `./scripts/brepl`
3. Browse to `http://localhost:9000` (you should see `Hello world!` in the web console)
4. (back to step 3) you should now see the REPL prompt: `cljs.user=>`
5. You may now evaluate ClojureScript statements in the browser context.
    
For more info using the browser as a REPL environment, see
[this](https://github.com/clojure/clojurescript/wiki/The-REPL-and-Evaluation-Environments#browser-as-evaluation-environment).
    
Clean project specific out:

    lein clean
     
Build a single release artifact with the following script and then open `index_release.html` in your browser.

    ./scripts/release

## License

Copyright © 2017 David Goldfarb

Distributed under the Eclipse Public License either version 1.0 or (at your option) any later version.
