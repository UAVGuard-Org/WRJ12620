package com.uavguard.wrj12620;

import com.uavguard.sdk.Action;

public class Land extends Action {

    private static final String name = "Pousar";

    private static final byte[] packet = new byte[] {
        (byte) 0x66,
        (byte) 0x80,
        (byte) 0x80,
        (byte) 0x80,
        (byte) 0x80,
        (byte) 0x02,
        (byte) 0x02,
        (byte) 0x99,
    };

    public Land() {
        super(name, packet);
    }
}
