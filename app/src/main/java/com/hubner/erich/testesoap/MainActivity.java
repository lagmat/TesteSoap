package com.hubner.erich.testesoap;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class MainActivity extends AppCompatActivity {

    String TAG = "Response";
    SoapPrimitive resultString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void ChamarSoap(View v) {

        TesteSoap task = new TesteSoap();
        task.execute();

    }

    private class TesteSoap extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            Log.i(TAG, "onPreExecute");
        }

        @Override
        protected Void doInBackground(Void... params) {
            //Log.i(TAG, "doInBackground");
            IncluirVersao();
            //Teste();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Log.i(TAG, "onPostExecute");
            Toast.makeText(MainActivity.this, resultString.toString(), Toast.LENGTH_LONG).show();
        }

    }


    public void Teste() {
        String valor = "Erich Hubner";
        String SOAP_ACTION = "http://tempuri.org/IBrPayService/teste";
        String METHOD_NAME = "teste";
        String NAMESPACE = "http://tempuri.org/";
        String URL = "https://www.hubnersoft.com/brpaysoap/BrPayService.svc?wsdl";
        //String URL = "http://10.1.1.51/web/dotnet/brpaysoap/BrPayService.svc?wsdl";

        try {

            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            Request.addProperty("valor", valor);

            SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelope.dotNet = true;
            soapEnvelope.setOutputSoapObject(Request);

            HttpTransportSE transport = new HttpTransportSE(URL);

            transport.call(SOAP_ACTION, soapEnvelope);
            resultString = (SoapPrimitive) soapEnvelope.getResponse();

            Log.i(TAG, resultString.toString());
        } catch (Exception ex) {
            Log.e(TAG, "Error: " + ex.getMessage());
        }
    }

    public void IncluirVersao() {
        String SOAP_ACTION = "http://tempuri.org/IBrPayService/IncluirVersao";
        String METHOD_NAME = "IncluirVersao";
        String NAMESPACE = "http://tempuri.org/";
        String URL = "https://www.hubnersoft.com/brpaysoap/BrPayService.svc?wsdl";
        //String URL = "http://10.1.1.51/web/dotnet/brpaysoap/BrPayService.svc?wsdl";

        try {

            versao vs = new versao();
            vs.setPlataforma("IOS");
            vs.setVersao1("10");

            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);

            Request.addProperty("versao", vs.getVersao1());
            Request.addProperty("plataforma",vs.getPlataforma());


            SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelope.dotNet = true;
            soapEnvelope.setOutputSoapObject(Request);

            HttpTransportSE transport = new HttpTransportSE(URL);

            transport.call(SOAP_ACTION, soapEnvelope);
            resultString = (SoapPrimitive) soapEnvelope.getResponse();

            Log.i(TAG, resultString.toString());
        } catch (Exception ex) {
            Log.e(TAG, "Error: " + ex.getMessage());
        }
    }
}
