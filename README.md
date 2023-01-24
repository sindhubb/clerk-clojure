## What is this?

This is a repository will familiarise you with basics of Clojure.
This repository uses Uses [Clerk](https://github.com/nextjournal/clerk) to show up as notebook.

## Where to see this stuff?

https://sindhubb.github.io/clerk-clojure/

## How to run this to contribute?

1. Install dependencies with `lein deps` 
2. Run `lein start-clerk <PORT>` where PORT can be any port you want
3. Hit Ctrl + S on a clojure file (save the file)
4. Browse `http://localhost:<PORT>` to see the live notebook

## How to get HTML?

Run `lein make-docs` and you should see html files generated under `src/<name-of-dir>`

## How to view HTML?

You can open `./docs/index.html` in any browser and browse as you normally would. 

## How to run this app for development?

* `lein ring server-headless <PORT>` (gives hot reloading) 
* `lein run <PORT>` (gives a simple server)

