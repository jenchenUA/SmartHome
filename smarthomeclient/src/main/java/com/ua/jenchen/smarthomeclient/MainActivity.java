package com.ua.jenchen.smarthomeclient;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Switch;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity
public class MainActivity extends AppCompatActivity {

    @ViewById(R.id.lampOne)
    public Switch lampOne;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(AppConstants.LIGHT_CONFIGURATION_TABLE_NAME);
//        LightConfiguration configuration = new LightConfiguration();
//        configuration.setInputPin("BCM27");
//        configuration.setOutputPin("BCM17");
//        configuration.setLabel("124123");
//        configuration.setOutputHighActivation(true);
//        configuration.setUid(configuration.getInputPin() + configuration.getOutputPin());
//        LightConfiguration configuration2 = new LightConfiguration();
//        configuration2.setInputPin("BCM23");
//        configuration2.setOutputPin("BCM22");
//        configuration2.setLabel("12414323");
//        configuration2.setOutputHighActivation(true);
//        configuration2.setUid(configuration2.getInputPin() + configuration2.getOutputPin());
//        reference.child(configuration.getUid()).setValue(configuration);
//        reference.child(configuration2.getUid()).setValue(configuration2);
//
//        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference(AppConstants.LIGHT_STATE_TABLE_NAME);
//        reference1.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    LampState value = snapshot.getValue(LampState.class);
//                    if (value != null && value.getUid().equals("BCM27BCM17")) {
//                        lampOne.setChecked(value.getState());
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

    }


//    @CheckedChange(R.id.lampOne)
//    public void click(Switch hello) {
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(AppConstants.LIGHT_STATE_TABLE_NAME);
//        LampState lampState = new LampState();
//        lampState.setState(hello.isChecked());
//        lampState.setUid("BCM27BCM17");
//        reference.child("341").setValue(lampState);
//    }
//
//    @CheckedChange(R.id.lampTwo)
//    public void click2(Switch hello) {
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference(AppConstants.LIGHT_STATE_TABLE_NAME);
//        LampState lampState = new LampState();
//        lampState.setState(hello.isChecked());
//        lampState.setUid("BCM23BCM22");
//        reference.child("3441").setValue(lampState);
//    }
}
