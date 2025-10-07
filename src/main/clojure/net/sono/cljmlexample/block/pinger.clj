(ns net.sono.cljmlexample.block.pinger
  (:require [net.sono.cljml.block :refer [defblock]])
  (:import (net.minecraft.network.chat Component)
           (net.minecraft.world InteractionHand InteractionResultHolder ItemInteractionResult)
           (net.minecraft.world.entity.player Player)
           (net.minecraft.world.level Level)
           (net.minecraft.world.level.block.state BlockBehaviour BlockBehaviour$Properties)))

(defn use-pinger
  [^Level world ^Player player ^InteractionHand hand]
  (println "Extractor used here!")

  (when-not (.isClientSide world)
    (.sendSystemMessage player
                        (Component/literal "Extractor used on server!")))

  (InteractionResultHolder/sidedSuccess
    (.getItemInHand player hand)
    (.isClientSide world)))


(defblock pinger-block
  "A block that does something when right-clicked on."
  :override {:useItemOn (fn [this stack bs level pos player hand hit-result]
                          (use-pinger level player hand)
                          ItemInteractionResult/SUCCESS)}
  :properties (-> (BlockBehaviour$Properties/of)
                  (.strength 3.5)))
