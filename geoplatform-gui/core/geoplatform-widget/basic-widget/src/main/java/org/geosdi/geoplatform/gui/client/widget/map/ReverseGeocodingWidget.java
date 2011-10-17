/*
 *  geo-platform
 *  Rich webgis framework
 *  http://geo-platform.org
 * ====================================================================
 *
 * Copyright (C) 2008-2011 geoSDI Group (CNR IMAA - Potenza - ITALY).
 *
 * This program is free software: you can redistribute it and/or modify it 
 * under the terms of the GNU General Public License as published by 
 * the Free Software Foundation, either version 3 of the License, or 
 * (at your option) any later version. This program is distributed in the 
 * hope that it will be useful, but WITHOUT ANY WARRANTY; without 
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR 
 * A PARTICULAR PURPOSE. See the GNU General Public License 
 * for more details. You should have received a copy of the GNU General 
 * Public License along with this program. If not, see http://www.gnu.org/licenses/ 
 *
 * ====================================================================
 *
 * Linking this library statically or dynamically with other modules is 
 * making a combined work based on this library. Thus, the terms and 
 * conditions of the GNU General Public License cover the whole combination. 
 * 
 * As a special exception, the copyright holders of this library give you permission 
 * to link this library with independent modules to produce an executable, regardless 
 * of the license terms of these independent modules, and to copy and distribute 
 * the resulting executable under terms of your choice, provided that you also meet, 
 * for each linked independent module, the terms and conditions of the license of 
 * that module. An independent module is a module which is not derived from or 
 * based on this library. If you modify this library, you may extend this exception 
 * to your version of the library, but you are not obligated to do so. If you do not 
 * wish to do so, delete this exception statement from your version. 
 *
 */
package org.geosdi.geoplatform.gui.client.widget.map;

import org.geosdi.geoplatform.gui.client.widget.map.event.reversegeocoding.ReverseGeocodingDispatchEvent;
import org.geosdi.geoplatform.gui.client.widget.map.event.reversegeocoding.ReverseGeocodingEventHandler;
import org.geosdi.geoplatform.gui.client.widget.map.marker.advanced.ReverseGeocodingVectorMarker;
import org.geosdi.geoplatform.gui.client.widget.map.popup.PopupMapWidget;
import org.geosdi.geoplatform.gui.client.widget.map.popup.template.PopupTemplate;
import org.geosdi.geoplatform.gui.configuration.message.GeoPlatformMessage;
import org.geosdi.geoplatform.gui.impl.map.GeoPlatformMap;
import org.geosdi.geoplatform.gui.puregwt.GPToolbarActionHandlerManager;
import org.geosdi.geoplatform.gui.puregwt.event.UpdateModelEvent;
import org.geosdi.geoplatform.gui.puregwt.geocoding.GPGeocodingHandlerManager;
import org.gwtopenmaps.openlayers.client.LonLat;
import org.gwtopenmaps.openlayers.client.event.MapClickListener;

/**
 * @author Giuseppe La Scaleia - CNR IMAA geoSDI Group
 * @email giuseppe.lascaleia@geosdi.org
 * 
 * @author Michele Santomauro - CNR IMAA geoSDI Group
 * @email michele.santomauro@geosdi.org
 * 
 */
public class ReverseGeocodingWidget implements ReverseGeocodingEventHandler {

    private GeoPlatformMap mapWidget;
    /** TODO : Think a way to have this in configuration **/
    private ReverseGeocodingVectorMarker rGMarker = new ReverseGeocodingVectorMarker(); //new ReverseGeocodingMarker();
    private PopupMapWidget popupWidget = new PopupMapWidget();
    private MapClickListener listener;
    private LonLat lonlat;
    private ReverseGeocodingDispatchEvent event;
    private boolean busy;

    public ReverseGeocodingWidget(GeoPlatformMap theMapWidget) {
        this.mapWidget = theMapWidget;
        GPGeocodingHandlerManager.addHandler(ReverseGeocodingEventHandler.TYPE, this);
        this.createListener();
        this.event = new ReverseGeocodingDispatchEvent(this);
    }

    @Override
    public void register() {
        GeoPlatformMessage.infoMessage("Reverse Geocoding",
                "Click on the map to have Information.");
        this.mapWidget.getMap().addLayer(this.rGMarker.getMarkerLayer());
        this.rGMarker.addControl(this.mapWidget.getMap());
        this.mapWidget.getMap().addMapClickListener(listener);
    }

    @Override
    public void unregister() {
        GeoPlatformMessage.infoMessage("Reverse Geocoding",
                "Reverse Geocoding Control Deactivated.");
        this.rGMarker.removeControl(this.mapWidget.getMap());
        this.clearWidgetStatus();
    }

    @Override
    public void onAddMarkerByLatLon(LonLat theLonLat) {
        this.lonlat = theLonLat;
        addMarker();
    }

    /**
     * Clear Widget : 1) Remove Marker Layer from Map 2) MapClickListener from
     * Map 3) Popup from Map 4) Marker from rGMarcker
     * 
     */
    public void clearWidgetStatus() {
        this.removeMapElements();
        this.mapWidget.getMap().removeLayer(this.rGMarker.getMarkerLayer(),
                false);
        this.mapWidget.getMap().removeMapClickListener(listener);
    }

    /**
     * 
     * @param location
     */
    public void onRequestSuccess(String location) {
        this.mapWidget.getMap().removePopup(this.popupWidget.getPopup());
        if (!location.equalsIgnoreCase(PopupTemplate.ZERO_RESULTS.toString())) {
            this.popupWidget.setContentHTML(PopupTemplate.IMAGE_RESULT_FOUND.toString() + "<br />" + location);
        } else {
            this.popupWidget.setContentHTML(PopupTemplate.IMAGE_RESULT_NOT_FOUND.toString()
                    + "<br /> "
                    + PopupTemplate.ZERO_RESULTS.toString());
        }

        this.mapWidget.getMap().addPopup(this.popupWidget.getPopup());
        this.busy = false;

        GPToolbarActionHandlerManager.fireEvent(new UpdateModelEvent(location));
    }

    /**
     * 
     * @param message
     */
    public void onRequestFailure(String message) {
        this.popupWidget.setContentHTML(PopupTemplate.IMAGE_SERVICE_ERROR.toString() + "<br />" + message);
        this.mapWidget.getMap().addPopupExclusive(this.popupWidget.getPopup());
        this.busy = false;
    }

    private void removeMapElements() {
        this.mapWidget.getMap().removePopup(this.popupWidget.getPopup());
        this.rGMarker.removeMarker();
    }

    private void createListener() {
        this.listener = new MapClickListener() {

            @Override
            public void onClick(MapClickEvent mapClickEvent) {
                lonlat = mapClickEvent.getLonLat();
                addMarker();
            }
        };
    }

    private void addMarker() {
        if (!busy) {
            busy = true;
            removeMapElements();
            sendRequest();
        } else {
            displayErrorMessage();
        }
    }

    private void displayErrorMessage() {
        GeoPlatformMessage.alertMessage("Reverse Geocoding",
                "Server busy.");
    }

    /**
     * Send Request to Reverse Geocoding Service
     */
    private void sendRequest() {
        this.rGMarker.addMarker(this.lonlat, this.mapWidget.getMap());
        addPopupAndFireEvent();
    }

    @Override
    public void onUpdateReverseGeocoding(LonLat ll) {
        this.lonlat = ll;
        updateMarker();
    }

    private void updateMarker() {
        if (!busy) {
            busy = true;
            sendRequestForUpdate();
        } else {
            displayErrorMessage();
        }
    }

    private void sendRequestForUpdate() {
        this.mapWidget.getMap().removePopup(this.popupWidget.getPopup());
        addPopupAndFireEvent();
    }

    private void addPopupAndFireEvent() {
        popupWidget.setLonLat(this.lonlat);
        this.popupWidget.setContentHTML(PopupTemplate.IMAGE_LOADING.toString()
                + PopupTemplate.MESSAGE_LOADING.toString());
        this.mapWidget.getMap().addPopup(popupWidget.getPopup());

        GPGeocodingHandlerManager.fireEvent(event);
    }

    /**
     * @return the lonlat with the Map Projection
     */
    public LonLat getLonlat() {
        LonLat lt = new LonLat(this.lonlat.lon(), this.lonlat.lat());
        lt.transform(this.mapWidget.getMap().getProjection(), "EPSG:4326");
        return lt;
    }

    /**
     * 
     * @return PopupMapWidget
     */
    public PopupMapWidget getPopupWidget() {
        return popupWidget;
    }
}
