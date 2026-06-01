# Project Codex Instructions

Be extremely concise, sacrifice grammar for concision.

## Clojure Tooling

- Use `clj-nrepl-eval --discover-ports` to find running nREPLs.
- Use `clj-nrepl-eval -p <port> "<form>"` for REPL evaluation and test probing.
- After editing Clojure or ClojureScript files, run `clj-paren-repair <changed-files>` before tests.
- Prefer `clj-paren-repair` over hand-editing unbalanced delimiters.

## TDD Skill: Project Testing Rules

When using the `tdd` skill in this project:

- Treat browser-visible behavior as the main public interface for app features.
- Prefer semantic Playwright tests in `tests/e2e`, especially shared flows in `tests/e2e/shared/appSmoke.ts`.
- Use user-visible locators: `getByRole`, `getByLabel`, and visible text.
- Do not add CLJS Playwright tests, visual snapshots, broad test-only browser globals, or implementation-detail assertions.
- Use the REPL only for debugging/probing, not as the test runner of record.
- For RED, write at least one failing semantic browser test for one observable behavior, consider edge cases that might be reached where code might fail.
- For GREEN, make the smallest ClojureScript/app change that passes.
- For REFACTOR, improve design only while tests are green.

Fast loop:

```sh
npm run test:e2e
```

Useful narrow checks:

```sh
npm run lint:clj
npm run compile
```

Full confidence gate before handoff, commit, or push:

```sh
npm run verify
```

`npm run verify` runs Clojure/CLJS/EDN linting, dev compile, dev E2E tests, and release E2E smoke tests.

Run `npm run dev` once and keep it running during browser-test development. The app is served at <http://localhost:8000/index.html>; Shadow nREPL uses port `7888`.

See `DEVELOPMENT_TESTING_DECISIONS.md` for rationale.
