/*
 * Copyright 2015 © Johnnie Ruffin
 *
 * Unless required by applicable law or agreed to in writing, software is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *
 */

package com.google.android.vending.licensing;

/**
 * A DeviceLimiter that doesn't limit the number of devices that can use a
 * given user's license.
 * <p>
 * Unless you have reason to believe that your application is being pirated
 * by multiple users using the same license (signing in to Market as the same
 * user), we recommend you use this implementation.
 */
public class NullDeviceLimiter implements DeviceLimiter {

    public int isDeviceAllowed(String userId) {
        return Policy.LICENSED;
    }
}
