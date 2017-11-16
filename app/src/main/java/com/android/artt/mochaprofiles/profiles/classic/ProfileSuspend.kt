package com.android.artt.mochaprofiles.profiles.classic

import com.android.artt.mochaprofiles.base.ProfileBase


class ProfileSuspend : ProfileBase(), ProfileBase.smartmaxGovernor {
    override val scaling_max_freq: String
        get() = "1044000"
    override val scaling_min_freq: String
        get() = "51000"
    override val gpu_floor_state: String
        get() = "1"
    override val gpu_floor_rate: String
        get() = "72000000"
    override val gpu_cap_state: String
        get() = "1"
    override val gpu_cap_rate: String
        get() = "396000000"
    override val boost_freq: String
        get() = "1326000"
    override val boost_time: String
        get() = "100"
    override val boost_emc: String
        get() = "0"
    override val boost_gpu: String
        get() = "252000"
    override val boost_cpus: String
        get() = "1"
    override val scheduler: String
        get() = noop
    override val governor: String
        get() = smartmax
    override val max_cpu_online: String
        get() = "2"
    override val min_cpu_online: String
        get() = "1"
    override val awake_ideal_freq: String
        get() = "564000"
    override val smartmax_boost_freq: String
        get() = "960000"
    override val touch_poke_freq: String
        get() = "828000"
    override val input_boost_duration: String
        get() = "60000"
}