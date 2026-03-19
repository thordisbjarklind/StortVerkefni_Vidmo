/******************************************************************************
 *  Nafn    : Ebba Þóra Hvannberg
 *  T-póstur: ebba@hi.is
 *  Lýsing  : er module skrá sem skilgreinir hvaða forritasöfn eru nauðsynleg og hver eru
 *  aðgengileg forritasöfnum  *
 *
 *****************************************************************************/
module Ludo {
    requires javafx.fxml;
    requires javafx.controls;
    opens is.vidmot to javafx.fxml;

    exports is.vidmot;
    exports is.vinnsla;
}