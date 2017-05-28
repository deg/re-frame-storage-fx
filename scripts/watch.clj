(require '[cljs.build.api :as b])

(b/watch "src"
  {:main 're-frame-storage-fx.core
   :output-to "out/re_frame_storage_fx.js"
   :output-dir "out"})
