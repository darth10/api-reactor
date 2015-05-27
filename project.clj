(defproject api-reactor "0.0.1-SNAPSHOT"
  :description "Automagical REST API server"
  :url "https://github.com/darth10/api-reactor"
  :license {:name "MIT"}
  :main api-reactor.main
  :aot [api-reactor.main]
  :uberjar-name "api-reactor-standalone.jar"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [ring/ring-core "1.1.8"]
                 [http-kit "2.1.16"]
                 [org.clojure/data.json "0.2.1"]
                 [org.clojure/java.jdbc "0.3.7"]
                 [org.clojure/tools.cli "0.2.2"]
                 [org.clojure/tools.logging "0.2.6"]
                 [ch.qos.logback/logback-classic "1.0.1"]
                 [mysql/mysql-connector-java "5.1.6"]])
