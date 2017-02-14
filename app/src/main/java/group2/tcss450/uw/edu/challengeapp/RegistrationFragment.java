package group2.tcss450.uw.edu.challengeapp;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RegistrationFragment extends Fragment implements View.OnClickListener {



    private OnFragmentInteractionListener mListener;
    private View testView;
    private static final String PARTIAL_URL
            = "http://cssgate.insttech.washington.edu/" +
            "~jamesh21/registration.php?";
    private String mUsername;
    private String mPassword;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        testView = inflater.inflate(R.layout.fragment_registration, container, false);
        Button button = (Button) testView.findViewById(R.id.submitRegistration);
        button.setOnClickListener(this);
        return testView;
    }

//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        if (mListener != null) {
            EditText editText = (EditText) testView.findViewById(R.id.registerLogin);
            mUsername = editText.getText().toString();
            editText = (EditText) testView.findViewById(R.id.registerPassword);
            mPassword = editText.getText().toString();
            AsyncTask<String, Void, String> task = new GetWebServiceTask();
            task.execute(PARTIAL_URL, mUsername, mPassword);

        }
    }

    private boolean checker(String result) {
        if (result.startsWith("Correct")) {
            System.out.println("In checker");
            return true;
        }
        return false;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
//     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(int buttonId, String userName, String password);
    }
    private class GetWebServiceTask extends AsyncTask<String, Void, String> {
        //private final String SERVICE = "_get.php";
        @Override
        protected String doInBackground(String... strings) {
            if (strings.length != 3) {
                throw new IllegalArgumentException("Two String arguments required.");
            }
            String response = "";
            HttpURLConnection urlConnection = null;
            String url = strings[0];
            String args = "username=" + strings[1] + "&password=" + strings[2];
            try {
                URL urlObject = new URL(url + args);
                System.out.println("this is the url" + urlObject.toString());
                urlConnection = (HttpURLConnection) urlObject.openConnection();
                InputStream content = urlConnection.getInputStream();
                BufferedReader buffer = new BufferedReader(new InputStreamReader(content));
                String s = "";
                while ((s = buffer.readLine()) != null) {
                    response += s;
                }
            } catch (Exception e) {
                response = "Unable to connect, Reason: "
                        + e.getMessage();
            } finally {
                if (urlConnection != null)
                    urlConnection.disconnect();
            }
            return response;
        }
        @Override
        protected void onPostExecute(String result) {
            // Something wrong with the network or the URL.
            if (result.startsWith("Unable to")) {
                // Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG)
                //       .show();
                return;
            }
            if (checker(result)) {
                System.out.println("onFragment NExt");
                mListener.onFragmentInteraction(R.id.submitLogin, mUsername, mPassword);
            }
        }
    }
}
