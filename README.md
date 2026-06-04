# barebones-cljs

Neil/deps-new template for a small Shadow CLJS + Reagent app with browser-first Playwright tests.

## Use

```sh
neil new io.github.jamiepratt/barebones-cljs my/app --latest-sha
cd app
npm install
npm run verify
```

Generated projects include Shadow CLJS, Reagent, `clj-kondo`, `clojure-lsp` config, semantic Playwright E2E tests, generated README/AGENTS docs, and a full `npm run verify` gate.

The visible app title is derived from the artifact id: `my/cool-app` becomes `Cool App`.

## Local Template Check

Fast template tests:

```sh
clojure -T:build test
```

Generate a sample app:

```sh
clojure -T:build generate-sample
```

Full generated-project confidence gate:

```sh
clojure -T:build verify-generated
```

`verify-generated` creates `target/generated/cool-app`, runs `npm install`, then runs the generated `npm run verify`.

## Local Raw deps-new

```sh
clojure -T:new create :template jamiepratt/barebones-cljs :name my/app
```
