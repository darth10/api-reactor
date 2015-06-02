(ns api-reactor.main
  (:gen-class)
  (:require [clojure.pprint :as pp]
            [org.httpkit.server :as http-kit]
            [api-reactor.config :as conf]
            [clojure.tools.logging :as log]
            [clojure.tools.cli :refer [cli]]))

(defonce server (atom nil))

(defn app [req]
  (log/info (str (:request-method req) " -> " (:uri req)))
  (pp/pprint (:headers req))
  {:status  200
   :headers {"Content-Type" "text/plain"}
   :body    (concat "hello HTTP '" (:uri req) "' !")})

(defn start-server [port thread]
  (when-not (nil? @server) (@server))   ; stop server
  (reset! server
          (http-kit/run-server app
                               {:port port
                                :thread thread})))

(defn- to-int [s] (Integer/parseInt s))

(defn print-and-exit
  [banner]
  (println banner)
  (System/exit 0))

(defn -main [& args]
  (let [[options _ banner]
        (cli args
             ["-p" "--port" "Port to listen" :default 1337 :parse-fn to-int]
             ["--thread" "Http worker thread count" :default 4 :parse-fn to-int]
             ["--profile" "dev or prod" :default :dev :parse-fn keyword]
             ["--[no-]help" "Print this help"])]
    (when (:help options) (print-and-exit))
    (conf/set-config options)
    (start-server (conf/get-config :port) (conf/get-config :thread))
    (log/info (str "server started. listen on 0.0.0.0@" (conf/get-config :port)))))
