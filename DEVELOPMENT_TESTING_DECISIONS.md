# Development And Testing Decisions

## Development Workflow

- Use Shadow CLJS with two build labels:
  - `:dev` for local watch mode and the development server.
  - `:release` for optimized release smoke testing.
- Keep `npm run dev` as the main local development command.
- The dev server serves the app at <http://localhost:8000/index.html>.
- Shadow nREPL runs on port `7888`.
- Use the REPL as a development accelerator, not as the test runner of record.
- Use the REPL for quick evals, state inspection, state pokes, and debugging.
- Keep automated tests reproducible without requiring an already-connected REPL.

## App Structure

- Use Reagent for the app so the project demonstrates realistic CLJS frontend state and rendering.
- Keep `barebones-cljs.core/app-state` public so it can be inspected or tweaked from the REPL during development.
- Keep browser tests DOM-first and user-visible, even though REPL access exists.

## Browser Testing

- Use plain Playwright for end-to-end tests.
- Do not write Playwright tests in CLJS for now.
- Prefer semantic browser tests over implementation-detail tests.
- Assert visible headings, labels, buttons, text, and user-visible state.
- Prefer Playwright locators such as `getByRole`, `getByLabel`, and visible text.
- Avoid visual snapshot testing for now.

## Fast Local Loop

- Run `npm run dev` once and keep it running.
- Run `npm run test:e2e` repeatedly during development.
- Playwright should reuse the existing Shadow dev server when it is already running.
- Use Chromium only for the initial fast suite.
- Add mobile and broader browser coverage later when the app needs it.

## Release Smoke Testing

- Keep release output isolated from the dev build.
- The `:dev` build writes to `public/js`.
- The `:release` build writes to `target/release/public/js`.
- Release smoke testing copies `public/index.html` to `target/release/public/index.html`.
- Release smoke tests serve `target/release/public` on `localhost:4173`.
- Dev and release tests share the same semantic smoke flow.

## Static Checks

- Treat `clj-kondo` as a required project check.
- Use `npm run lint:clj` for Clojure, ClojureScript, and EDN linting.
- Include `clj-kondo` in the full verification command.
- `clj-kondo` must be installed on `PATH` for `npm run verify` to complete.

## Verification

- Use `npm run verify` as the confidence gate before handoff, commit, or push.
- `verify` should run:
  - `npm run lint:clj`
  - `npm run compile`
  - `npm run test:e2e`
  - `npm run test:e2e:release`

## Explicit Non-Goals For Now

- No visual regression testing yet.
- No TypeScript or ESLint verification gate yet.
- No mobile Playwright projects yet.
- No backend/API fixtures yet.
- No broad test-only browser globals.
- No helper script for copying release HTML.
