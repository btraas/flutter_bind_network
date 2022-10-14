package com.agartee.plugins;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.util.Log;

public class NetworkBinder {

    String name;
    private int transport;
    public NetworkBinder(int transport, String name) {
        this.transport = transport;
        this.name = name;
    }
    public void bind(Context context, Runnable callback) {
        ConnectivityManager connection_manager =
                (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkRequest.Builder request = new NetworkRequest.Builder();
        request.addTransportType(transport);

        connection_manager.requestNetwork(request.build(), new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(Network network) {
                Log.i(name,"Binding to network: " + network.toString());
                connection_manager.bindProcessToNetwork(network);
                Network an = connection_manager.getActiveNetwork();
                NetworkInfo ni = connection_manager.getActiveNetworkInfo();
                callback.run();
            }

            @Override
            public void onCapabilitiesChanged (Network network,
                                               NetworkCapabilities networkCapabilities) {
                Log.i(name,"Binding to network: " + network.toString());
                connection_manager.bindProcessToNetwork(network);
                Network an = connection_manager.getActiveNetwork();
                NetworkInfo ni = connection_manager.getActiveNetworkInfo();
                callback.run();
            }
        });
    }

    public void unbind(Context context) {
        ConnectivityManager connection_manager =
                (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        Log.i(name,"Unbinding from networks");
        connection_manager.bindProcessToNetwork(null);
    }
}
