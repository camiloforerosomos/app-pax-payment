package com.example.paxpayment;

import android.graphics.PostProcessor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.pax.poslink.CommSetting;
import com.pax.poslink.POSLinkAndroid;
import com.pax.poslink.PaymentRequest;
import com.pax.poslink.PaymentResponse;
import com.pax.poslink.PosLink;
import com.pax.poslink.ProcessTransResult;
import com.pax.poslink.base.BaseRequest;
import com.pax.poslink.poslink.POSLinkCreator;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Main Activity";

    private SectionsPageAdapter mSectionsPageAdapter;
    private TabLayout tabLayout;
    private ViewPager2 mViewPager;
    private Button btnPayment;

    private PosLink link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
        // Initialize
        mViewPager = findViewById(R.id.view_pager);
        // this.setupViewPage(mViewPager);
        TabLayout tabLayout = findViewById(R.id.tabs);

        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager(), getLifecycle());

        mSectionsPageAdapter.addFragment(new PaymentFragment(), "Payment");

        mViewPager.setAdapter(mSectionsPageAdapter);
        tabLayout.addTab(tabLayout.newTab().setText("Payment"));
        */

        btnPayment = findViewById(R.id.btnPayment);

        btnPayment.setOnClickListener(v -> {
            Toast.makeText(getApplicationContext(), "Testeando Payment1", Toast.LENGTH_SHORT).show();
        });

        init();
    }

    /*@Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);

        btnPayment = view.findViewById(R.id.btnPayment);

        btnPayment.setOnClickListener(v -> {
            Toast.makeText(getActivity(), "Testeando Payment1", Toast.LENGTH_SHORT).show();
        });

        return view;
    }*/

    /*private void setupViewPage(ViewPager2 viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new PaymentFragment(), "Payment");
        viewPager.setAdapter(adapter);
    }*/

    private void init() {
        // Permisos para usar la app
        // PermissionUtil.init(getApplication());

        // CommSetting commSetting = setupSetting(getApplicationContext());
        CommSetting commSetting = new CommSetting();

        commSetting.setType(CommSetting.AIDL);

        commSetting.setTimeOut("60000");
        commSetting.setSerialPort("COM1");
        commSetting.setBaudRate("9600");
        commSetting.setDestIP("172.16.20.15");
        commSetting.setDestPort("10009");
        commSetting.setMacAddr("");
        commSetting.setEnableProxy(false);

        // Payment Request

        PaymentRequest paymentRequest = new PaymentRequest();

        // Medio de Pago: Cash
        paymentRequest.TenderType = 8;

        // Sale
        paymentRequest.TransType = 2;
        paymentRequest.Amount = "100";

        // link = new PosLink();
        POSLinkCreator posLinkCreator = new POSLinkCreator();
        link = posLinkCreator.createPoslink(getApplicationContext());

        // POSLinkAndroid.init(getApplicationContext());
        link.SetCommSetting(commSetting);
        link.PaymentRequest = paymentRequest;

        try {
            //ProcessTransResult result = link.ProcessTrans();

            //System.out.println("Codigo: " + result.Code + result.Msg);
            new asyncRequest().execute(paymentRequest);

        } catch (Exception e) {
            System.out.println("Error" + e);
        }
    }

    private class asyncRequest extends AsyncTask<BaseRequest, Integer, ProcessTransResult> {

        @Override
        protected ProcessTransResult doInBackground(BaseRequest... baseRequests) {

            BaseRequest request = baseRequests[0];

            // Do the thing
            ProcessTransResult result = link.ProcessTrans();

            return result;
        }

        @Override
        protected void onPostExecute(ProcessTransResult result) {
            System.out.println("Code: " + result.Code);
            System.out.println("Message: " + result.Msg);
            // super.onPostExecute(result);
        }
    }

    private static void doRequest(BaseRequest request) {

    }
}