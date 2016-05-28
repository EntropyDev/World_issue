package com.home.harsh.worldissues;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link IssuesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link IssuesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IssuesFragment extends Fragment implements FragmentManager.OnBackStackChangedListener,View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    View view;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    FragmentTransaction[] fragTran;
    P pf;S sf;H hf;
    FragmentManager fragmentManager;

    FragmentTransaction fragmentTransaction;
    private OnFragmentInteractionListener mListener;
    Button p,s,h;
    public IssuesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment IssuesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static IssuesFragment newInstance(String param1, String param2) {
        IssuesFragment fragment = new IssuesFragment();
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
        view =inflater.inflate(R.layout.fragment_issues, container, false);
        Bundle args= new Bundle();
        args.putString("Adding","true");
        fragmentManager=getFragmentManager();
        pf=new P();
        sf= new S();
        hf= new H();
        //fragmentTransaction=fragmentManager.beginTransaction();
        fragTran= new FragmentTransaction[1];
        p=(Button)view.findViewById(R.id.p);
        s=(Button)view.findViewById(R.id.s);
        h=(Button)view.findViewById(R.id.h);
        p.setOnClickListener(this);
        s.setOnClickListener(this);
        h.setOnClickListener(this);
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

    @Override
    public void onBackStackChanged() {
        boolean canback = ((AppCompatActivity) getActivity()).getSupportFragmentManager().getBackStackEntryCount()>0;
        Log.d("backstack",String.valueOf(canback));
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(canback);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.p:
                fragTran[0] = fragmentManager.beginTransaction();
                fragTran[0].replace(R.id.frame_container,pf);
                fragTran[0].addToBackStack(null);
                fragTran[0].commit();
                break;
            case R.id.s:
                fragTran[0] = fragmentManager.beginTransaction();
                fragTran[0].replace(R.id.frame_container,sf);
                fragTran[0].addToBackStack(null);
                fragTran[0].commit();
                break;
            case R.id.h:
                fragTran[0] = fragmentManager.beginTransaction();
                fragTran[0].replace(R.id.frame_container,hf);
                fragTran[0].addToBackStack(null);
                fragTran[0].commit();
                break;

        }
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
