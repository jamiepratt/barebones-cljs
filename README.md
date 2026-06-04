# Barebones CLJS

Small ClojureScript starter using Shadow CLJS.

## Development

Start the Reagent browser build, dev server, and Shadow nREPL:

```sh
npm run dev
```

Open <http://localhost:8000/index.html>. Shadow starts its nREPL on port `7888`.

For Calva jack-in, use the `:shadow` alias. Calva injects its own nREPL/CIDER deps with `-Sdeps`, then runs Shadow.

Use that REPL for quick probes while the app is running. For example, `barebones-cljs.core/app-state` is public so you can inspect or tweak Reagent state during debugging, but the automated tests stay browser-first.

Compile once:

```sh
npm run compile
```

## Tests

Run fast semantic browser tests against the dev build:

```sh
npm run test:e2e
```

Run the same smoke flow visibly:

```sh
npm run test:e2e:headed
```

Compile the isolated release build and smoke-test it:

```sh
npm run test:e2e:release
```

Run the full confidence gate:

```sh
npm run verify
```

`verify` runs `clj-kondo`, a dev compile, dev E2E tests, and release E2E tests. The browser tests are Chromium-first; mobile and broader browser projects can be added later when the app needs that coverage.
