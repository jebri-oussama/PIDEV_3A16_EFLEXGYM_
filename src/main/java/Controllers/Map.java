package Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ResourceBundle;

public class Map implements Initializable {

    @FXML
    private WebView webView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        WebEngine webEngine = webView.getEngine();
        String htmlContent = """
                <!DOCTYPE html>
                <html>
                  <head>
                    <meta name="viewport" content="initial-scale=1,maximum-scale=1,user-scalable=no" />
                    <title>Display a map</title>
                    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/ol@v8.2.0/ol.css">
                    <script src="https://cdn.jsdelivr.net/npm/ol@v8.2.0/dist/ol.js"></script>
                    <style>
                      #map {position: absolute; top: 0; right: 0; bottom: 0; left: 0;}
                    </style>
                  </head>
                  <body>
                    <div id="map">
                      <a href="https://www.maptiler.com" style="position:absolute;left:10px;bottom:10px;z-index:999;"><img src="https://api.maptiler.com/resources/logo.svg" alt="MapTiler logo"></a>
                    </div>
                    <script>
                      const key = 'fllBdXsja57pH8i41ZFG';

                      const attribution = new ol.control.Attribution({
                        collapsible: false,
                      });

                      const source = new ol.source.TileJSON({
                        url: `https://api.maptiler.com/maps/openstreetmap/tiles.json?key=${key}`, // source URL
                        tileSize: 512,
                        crossOrigin: 'anonymous'
                      });

                      const map = new ol.Map({
                        layers: [
                          new ol.layer.Tile({
                            source: source
                          })
                        ],
                        controls: ol.control.defaults.defaults({attribution: false}).extend([attribution]),
                        target: 'map',
                        view: new ol.View({
                          constrainResolution: true,
                          center: ol.proj.fromLonLat([0, 0]), // starting position [lng, lat]
                          zoom: 2 // starting zoom
                        })
                      });
                    </script>
                  </body>
                </html>
                """;
        webEngine.loadContent(htmlContent);
    }
}
