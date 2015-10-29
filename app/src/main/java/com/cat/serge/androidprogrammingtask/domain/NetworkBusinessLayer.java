package com.cat.serge.androidprogrammingtask.domain;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.cat.serge.androidprogrammingtask.eventbus.BusNotifier;
import com.cat.serge.androidprogrammingtask.eventbus.events.FailEvent;
import com.cat.serge.androidprogrammingtask.eventbus.events.StartEvent;
import com.cat.serge.androidprogrammingtask.eventbus.events.SuccessEvent;
import com.cat.serge.androidprogrammingtask.model.Credentials;
import com.cat.serge.androidprogrammingtask.model.WorldsResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.lang.ref.WeakReference;

/**
 * NetworkBusinessLayer
 *
 * @author korchinsky_s
 * @since 29.10.2015.
 */
public class NetworkBusinessLayer {

    private static final String TAG             = NetworkBusinessLayer.class.getSimpleName();
    private static final String URL             = "http://backend1.lordsandknights.com/XYRALITY/WebObjects/BKLoginServer.woa/wa/worlds";
    private static final int    READ_TIMEOUT    = 300000;
    private static final int    CONNECT_TIMEOUT = 100000;
    private WeakReference<Context> mCtx;

    public NetworkBusinessLayer(final Context ctx) {
        mCtx = new WeakReference<>(ctx);
    }

    public static NetworkBusinessLayer getInstance(Context ctx) {
        return new NetworkBusinessLayer(ctx);
    }

    public void getWorlds(@NonNull String login, @NonNull String password) {
        BusNotifier.notifyEvent(new StartEvent());


        Log.d(TAG, "++++++ Request started URL: " + URL + " ++++++");

        ResponseEntity response   = null;
        HttpStatus     statusCode = null;
        String         body       = null;

        try {
            RestTemplate restTemplate = initRestTemplate();
            HttpHeaders headers = initDefaultHeader();
            //noinspection UnusedAssignment
            HttpEntity<String> entity = null;

            Credentials request = new Credentials();
            request.setLogin(login);
            request.setPassword(password);
            request.setDeviceType(String.format("%s %s", android.os.Build.MODEL, android.os.Build.VERSION.RELEASE));
            request.setDeviceId(getMACAddress());

            entity = new HttpEntity<>(request.toRequest(), headers);

            Log.d(TAG, entity.getBody());

            response = restTemplate.exchange(URL, HttpMethod.POST, entity, String.class);

            Log.d(TAG, "Response Code: " + response.getStatusCode());
            Log.d(TAG, "Response Headers: " + response.getHeaders());
            Log.d(TAG, "Response Body: " + response.getBody());

            //noinspection ConstantConditions
            if (response != null) {
                statusCode = response.getStatusCode();
                body = (String) response.getBody();
            }
        } catch (HttpServerErrorException ex) {
            statusCode = ex.getStatusCode();
            body = ex.getResponseBodyAsString();
            Log.w(TAG, "HttpServerErrorException(" + statusCode + "): " + ex.getMessage());
            Log.w(TAG, "HttpServerErrorException(" + body + "): " + ex.getMessage());
        } catch (HttpStatusCodeException ex) {
            Log.w(TAG, "HttpStatusCodeException(" + statusCode + "): " + ex.getMessage());
            Log.w(TAG, "Response Code: " + ex.getStatusCode());
            Log.w(TAG, "Response Body: " + ex.getResponseBodyAsString());
            statusCode = ex.getStatusCode();
            body = ex.getResponseBodyAsString();
        } catch (ResourceAccessException ex) {
            Log.e(TAG, "ResourceAccessException" + ex.getMessage());
        } catch (RestClientException ex) {
            // Handle this from ResponseErrorHandler
            Log.e(TAG, "RestClientException" + ex.getMessage());
        } catch (HttpMessageNotReadableException ex) {
            Log.e(TAG, "HttpMessageNotReadableException" + ex.getMessage());
        } finally {
            Log.d(TAG, "-------- Request ended URL: " + URL + " --------");
            handleResponse(
                statusCode
                , body
            );
        }
    }

    private void handleResponse(final HttpStatus statusCode, final String body) {
        if (statusCode != null && !TextUtils.isEmpty(body)) {
            if (statusCode == HttpStatus.OK || statusCode == HttpStatus.CREATED || statusCode == HttpStatus.NO_CONTENT) {
                try {
                    ObjectMapper mapper = new ObjectMapper();

                    //noinspection unchecked because catch exception.
                    WorldsResponse response = mapper.readValue(body, WorldsResponse.class);

                    BusNotifier.notifyEvent(new SuccessEvent(response.allAvailableWorlds));
                } catch (IOException e) {
                    Log.e(TAG, "IOException:" + e.getMessage());
                    BusNotifier.notifyEvent(new FailEvent());
                }
            }
        } else {
            BusNotifier.notifyEvent(new FailEvent());
        }
    }

    private String getMACAddress() {
        WifiManager manager = (WifiManager) mCtx.get().getSystemService(Context.WIFI_SERVICE);
        WifiInfo    info = manager.getConnectionInfo();
        return info.getMacAddress();
    }

    private RestTemplate initRestTemplate() {
        RestTemplate restTemplate = new RestTemplate(getClientHttpRequestFactory());
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        return restTemplate;
    }

    protected HttpHeaders initDefaultHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    /**
     * Create ClientHttpRequestFactory with a custom timeouts
     *
     * @return ClientHttpRequestFactory
     * @see ClientHttpRequestFactory
     */
    private ClientHttpRequestFactory getClientHttpRequestFactory() {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setReadTimeout(READ_TIMEOUT);
        factory.setConnectTimeout(CONNECT_TIMEOUT);
        return factory;
    }
}
