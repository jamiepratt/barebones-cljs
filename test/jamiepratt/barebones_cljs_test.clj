(ns jamiepratt.barebones-cljs-test
  (:require [clojure.edn :as edn]
            [clojure.java.io :as io]
            [clojure.spec.alpha :as s]
            [clojure.string :as str]
            [clojure.test :refer [deftest is testing]]
            [jamiepratt.barebones-cljs :as sut]
            [org.corfield.new :as deps-new]))

(def template-resource "io/github/jamiepratt/barebones_cljs/template.edn")
(def sample-dir "target/test-generated/cool-app")

(defn- slurp-in [base path]
  (slurp (io/file base path)))

(defn- files-under [dir]
  (filter #(.isFile %) (file-seq (io/file dir))))

(deftest valid-template-test
  (testing "template.edn is valid deps-new EDN"
    (let [template (edn/read-string (slurp (io/resource template-resource)))]
      (is (s/valid? :org.corfield.new/template template)
          (s/explain-str :org.corfield.new/template template)))))

(deftest display-name-test
  (is (= "Cool App" (sut/display-name "cool-app")))
  (is (= "Cool App" (:display-name (sut/data-fn {:artifact/id "cool_app"})))))

(deftest generated-sample-test
  (deps-new/create {:template 'jamiepratt/barebones-cljs
                    :name 'my/cool-app
                    :target-dir sample-dir
                    :src-dirs ["resources/io/github"]
                    :overwrite :delete})
  (let [base (io/file sample-dir)]
    (is (.exists (io/file base "README.md")))
    (is (.exists (io/file base "AGENTS.md")))
    (is (.exists (io/file base ".lsp/config.edn")))
    (is (.exists (io/file base "src/my/cool_app/core.cljs")))
    (is (not (.exists (io/file base "DEVELOPMENT_TESTING_DECISIONS.md"))))
    (is (str/includes? (slurp-in base "package.json") "\"name\": \"cool-app\""))
    (is (str/includes? (slurp-in base "deps.edn") ":name my/cool-app"))
    (is (str/includes? (slurp-in base "README.md") "# Cool App"))
    (is (str/includes? (slurp-in base "public/index.html") "<title>Cool App</title>"))
    (is (str/includes? (slurp-in base "shadow-cljs.edn") "my.cool-app.core/init"))
    (is (str/includes? (slurp-in base "src/my/cool_app/core.cljs") "(ns my.cool-app.core"))
    (is (str/includes? (slurp-in base "tests/e2e/shared/appSmoke.ts") "name: \"Cool App\""))
    (doseq [file (files-under base)]
      (is (not (str/includes? (slurp file) "{{"))
          (str "unresolved placeholder in " (.getPath file))))))
