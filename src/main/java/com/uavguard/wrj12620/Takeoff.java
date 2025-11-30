package com.uavguard.wrj12620;

import com.uavguard.sdk.Action;

public class Takeoff extends Action {

    private static final String name = "Decolar";

    private static final byte[] packet = new byte[] {
        (byte) 0x66,
        (byte) 0x80,
        (byte) 0x80,
        (byte) 0x80,
        (byte) 0x80,
        (byte) 0x01,
        (byte) 0x01,
        (byte) 0x99,
    };

    public Takeoff() {
        super(name, packet);
    }
}
