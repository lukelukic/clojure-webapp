(ns web-app.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))




(defn hello-middleware
  [handler]
  (fn [request]
    (println "Hello Zahteve")
    (handler request)))

(defn write-ip-middleware
  [handler]
  (fn [request]
    (println (request :remote-addr))
    (handler request)))


(defroutes api-routes
  (GET "/" [] "API"))

(defroutes web-routes
  (GET "/web" [] "WEB")
  (GET "/params/:a" [a ime] (println "Vrednost query parametra" ime) (str a)))

(defn not-found-handler
  [request]
  {:status 404
   :body "<h1>Page not found!</h1>"})

(def app
  (routes (-> api-routes (wrap-defaults site-defaults) write-ip-middleware hello-middleware)
          (-> web-routes (wrap-defaults site-defaults))
          (route/not-found not-found-handler)))

