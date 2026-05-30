(ns barebones-cljs.core)

(defn render []
  (let [app (.getElementById js/document "app")]
    (set! (.-innerHTML app)
          "<section class=\"app-shell\">
             <p class=\"eyebrow\">shadow-cljs + nREPL</p>
             <h1>Barebones CLJS</h1>
             <p>Edit <code>src/barebones_cljs/core.cljs</code> and save to hot reload.</p>
             <button id=\"hello\" type=\"button\">Click from CLJS</button>
           </section>")
    (when-let [button (.getElementById js/document "hello")]
      (.addEventListener button "click" #(js/alert "Hello from ClojureScript")))))

(defn ^:dev/after-load init []
  (render))
