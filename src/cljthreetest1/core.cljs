(ns cljthreetest1.core
  (:require [cljsjs.three]))

(def renderer-color 0xEEEEEE)
(def plane-color 0xCCCCCC)
(def cube-color 0xFF0000)
(def sphere-color 0x7777FF)

(defn create-renderer!
  [color]
  ((fn [renderer]
     (do
;       (.setClearColorHex renderer)
       (.setClearColor renderer (js/THREE.Color. color))
       (.setSize renderer (.-innerWidth js/window) (.-innerHeight js/window))
       renderer))
   (js/THREE.WebGLRenderer.)))

(defn create-scene!
  []
  (js/THREE.Scene.))

(defn create-camera!
  []
  (js/THREE.PerspectiveCamera. 45 (/ (.-innerWidth js/window) (.-innerHeight js/window)) 0.1 1000))

(defn create-sphere!
  [radius widthSegments heightSegments color]
  (js/THREE.Mesh.
    (js/THREE.SphereGeometry. radius widthSegments heightSegments)
    (js/THREE.MeshBasicMaterial. (js-obj "color" color "wireframe" true))))

(defn position-object!
  [object x y z]
  (do
    (set! (.-x (.-position object)) x)
    (set! (.-y (.-position object)) y)
    (set! (.-z (.-position object)) z)
    object))

(defn create-plane!
  [width height color]
  (js/THREE.Mesh.
    (js/THREE.PlaneGeometry. width height)
    (js/THREE.MeshBasicMaterial. (js-obj "color" color))))

(defn rotate-object!
  [object r]
  (do
    (set! (.-x (.-rotation object)) r)
    object))

(defn create-cube!
  [edgesize color]
  (js/THREE.Mesh.
    (js/THREE.BoxGeometry. edgesize edgesize edgesize)
    (js/THREE.MeshBasicMaterial. (js-obj "color" color "wireframe" true))))

(defn add-output-to-html!
  [scene camera renderer]
  (do
    (.lookAt camera (.-position scene))
    (.appendChild (.getElementById js/document "webGL-output") (.-domElement renderer))
    (.render renderer scene camera)
    nil))

(defn create-objects!
  []
  [(js/THREE.AxisHelper. 20)
   (rotate-object! (position-object! (create-plane! 60 20 plane-color) 15 0 0) (* -0.5 (.-PI js/Math)))
   (position-object! (create-sphere! 4 20 20 sphere-color) 20 4 2)
   (position-object! (create-cube! 4 cube-color) -4 3 0)])

(defn populate-scene!
  [scene objects]
  (do
    (doseq [object objects]
      (.add scene object))
    scene))

(defn ^:export init
  []
  (add-output-to-html! (populate-scene! (create-scene!) (create-objects!))
                       (position-object! (create-camera!) -30 40 30)
                       (create-renderer! renderer-color)))
