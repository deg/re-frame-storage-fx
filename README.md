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

Include this in your `project.clj`:

[![Clojars Project](https://img.shields.io/clojars/v/com.degel/re-frame-storage-fx.svg)](https://clojars.org/com.degel/re-frame-storage-fx)



In your event handler file (typically `handlers.cljs` or `events.cljs`), add this
package to your `ns`.

```clj
(ns ...
  (:require
    ...
    [com.degel.re-frame-storage-fx]
    ...))
```

Then write event handlers that use these effects and coeffects:

### Coeffects

Coeffects:

#### `:storage/get`

Get one or more items, by key, from local or session storage.

Note that the underlying API is a map of strings to strings, with no understanding of
keywords or other Clojure types. This library does minimal type coercions of keys or
values: just passing them through `str`. Effectively, this supports keyword keys by
converting them to similar-looking strings: `:my-key` ==> `":my-key"`.

Get item from local storage:

```clj
(re-frame/reg-event-fx
 :my-handler-get-from-local
 [(re-frame/inject-cofx :storage/get {:name :my-key})]
 (fn [{db :db my-key :storage/get}]
    ...))
```

Get item from session storage:

```clj
(re-frame/reg-event-fx
 :my-handler-get-from-session
 [(re-frame/inject-cofx :storage/get {:session? true :name :my-key})]
 (fn [{db :db my-key :storage/get}]
    ...))
```

Get multiple stored items:

```clj
(re-frame/reg-event-fx
 :my-handler-get-multiple
 [(re-frame/inject-cofx :storage/get {:names [:key1 :key2]})]
 (fn [{db :db {:keys [key1 key2]} :storage/get}]
    ...))
```


#### `:storage/all`

Return map of all keys and values in the local or session store.

TBD - Needs examples

#### `:storage/count`

Return count of all keys and values in the local or session store.

TBD - Needs examples


#### `:storage/empty?`

Return true if count is zero.

TBD - Needs examples

#### `:storage/keys`

Return sequence of all keys in local or session store.

TBD - Needs examples

#### `:storage/vals`

Return sequence of all values in local or session store.

TBD - Needs examples


### Effects

#### `:storage/set`

Add one or more items to local or session storage

```clj
(re-frame/reg-event-fx
 :my-handler
 (fn [{db :db} _]
   {:storage/set {:session? false
                  :name :my-key :value "Schlage"}}))

(re-frame/reg-event-fx
 :my-handler
 (fn [{db :db} _]
   {:storage/set {:session? true
                  :name :toiletry :value "toothpaste"}}))

(re-frame/reg-event-fx
 :my-handler
 (fn [{db :db} _]
   {:storage/set {:session? false
                  :pairs [{:name :key1 :value 42}
                          {:name :key2 :value 17}]}}))
```

#### `:storage/remove`

Remove one or more items from local or session storage

TBD - Needs examples



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
