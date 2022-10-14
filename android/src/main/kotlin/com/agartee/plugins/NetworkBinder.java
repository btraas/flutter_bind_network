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

        final boolean[] completed = {false};

        connection_manager.requestNetwork(request.build(), new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(Network network) {
                if(completed[0]) {
                    return;
                }

                Log.i(name,"Binding to network: " + network.toString());
                completed[0] = connection_manager.bindProcessToNetwork(network);
                Network an = connection_manager.getActiveNetwork();
                NetworkInfo ni = connection_manager.getActiveNetworkInfo();
                if(completed[0]) {
                    Log.i(name, "Bound to network: " + ni.getTypeName() + "/" + ni.getSubtypeName());
                    callback.run();
                }
            }

            @Override
            public void onCapabilitiesChanged (Network network,
                                               NetworkCapabilities networkCapabilities) {
                if(completed[0]) {
                    return;
                }
                Log.i(name,"Binding to network: " + network.toString());
                completed[0] =connection_manager.bindProcessToNetwork(network);
                Network an = connection_manager.getActiveNetwork();
                NetworkInfo ni = connection_manager.getActiveNetworkInfo();

                if(completed[0]) {
                    Log.i(name, "Bound to network: " + ni.getTypeName()+ "/" + ni.getSubtypeName());
                    callback.run();
                }
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

