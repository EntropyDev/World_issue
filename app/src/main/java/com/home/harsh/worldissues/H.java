package com.home.harsh.worldissues;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link H.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link H#newInstance} factory method to
 * create an instance of this fragment.
 */
public class H extends Fragment implements View.OnClickListener ,FragmentManager.OnBackStackChangedListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static String SCORE_H = "-";
    FragmentTransaction[] fragTran;
    FragmentManager fragmentManager;

    FragmentTransaction fragmentTransaction;
    Percentage percentage;

    Button v,m,t,submit;
    View view;
    TextView score1,score2,v_t,m_t,t_t;
    public  int score_h;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public H() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment H.
     */
    // TODO: Rename and change types and number of parameters
    public static H newInstance(String param1, String param2) {
        H fragment = new H();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);

            mParam2 = getArguments().getString(ARG_PARAM2);




        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_h, container, false);
        score1=(TextView)view.findViewById(R.id.score1);
        score2=(TextView)view.findViewById(R.id.score2);
        v=(Button)view.findViewById(R.id.V_minus);
        submit=(Button)view.findViewById(R.id.submit);
        Bundle args= new Bundle();
        args.putString("Adding","true");
        fragmentManager=getFragmentManager();
        fragTran= new FragmentTransaction[1];
        percentage = new Percentage();
        m=(Button)view.findViewById(R.id.M_minus);
        t=(Button)view.findViewById(R.id.T_minus);
        v.setOnClickListener(this);
        m.setOnClickListener(this);
        t.setOnClickListener(this);
        submit.setOnClickListener(this);

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

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
    int v_c = 0;
    int m_c = 0;
    int t_c = 0;
    @Override
    public void onClick(View v) {
        Toolbar toolbar=(Toolbar)getActivity().findViewById(R.id.toolbar);
        TextView textView;
        switch (v.getId()){
            case R.id.V_minus: {
                textView = (TextView) toolbar.findViewById(R.id.Volu_f);
                Integer a = new Integer(textView.getText().toString());
                int b = a.intValue();
                if(b>0)
                    b--;
                textView.setText(String.valueOf(b));
                Log.d("toolbar v", textView.getText().toString());
                v_c++;
                Log.d("V-min", String.valueOf(v_c));
                break;
            }
            case R.id.T_minus: {
                textView = (TextView) toolbar.findViewById(R.id.Tech_f);
                Integer a = new Integer(textView.getText().toString());
                int b = a.intValue();
                if(b>0)
                    b--;
                textView.setText(String.valueOf(b));
                t_c++;
                break;
            }
            case R.id.M_minus: {
                textView = (TextView) toolbar.findViewById(R.id.M_T_f);
                Integer a = new Integer(textView.getText().toString());
                int b = a.intValue();
                if(b>0)
                    b--;
                textView.setText(String.valueOf(b));
                m_c++;
                break;
            }
            case R.id.submit: {
                score_h = 0;
                if (t_c > v_c && v_c > m_c) {
                    if (m_c == 1 && v_c == 3 && t_c == 4) {
                        score_h = 10;

                    } else {
                        score_h = 8;
                    }
                } else {
                    score_h = 5;
                }
                Log.d("score", String.valueOf(score_h));
                SCORE_H = String.valueOf(score_h);
                newInstance(SCORE_H, String.valueOf(score_h));
                fragTran[0] = fragmentManager.beginTransaction();
                fragTran[0].replace(R.id.frame_container, percentage);
                fragTran[0].addToBackStack(null);
                fragTran[0].commit();
                break;
            }
        }
    }

    @Override
    public void onBackStackChanged() {

        boolean canback = ((AppCompatActivity) getActivity()).getSupportFragmentManager().getBackStackEntryCount()>0;
        Log.d("backstack",String.valueOf(canback));
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(canback);

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
