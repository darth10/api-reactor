(ns api-reactor.config)

(defonce app-configs (atom {:profile :dev}))

(defn get-config [key & [default]]
  (if-let [v (or (key @app-configs) default)]
    v
    (when-not (contains? @app-configs key)
      (throw (RuntimeException. (str "unknow config for key " (name key)))))))

(defn set-config [options]
  (swap! app-configs merge options))
