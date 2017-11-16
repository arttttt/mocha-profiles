package com.android.artt.mochaprofiles.profiles.alternative

import com.android.artt.mochaprofiles.base.ProfileBase

class ProfileLowAlt : ProfileBase(), ProfileBase.intelliactiveGovernor {
    override val scaling_max_freq: String
        get() = "1044000"
    override val scaling_min_freq: String
        get() = "204000"
    override val gpu_floor_state: String
        get() = "1"
    override val gpu_floor_rate: String
        get() = "72000000"
    override val gpu_cap_state: String
        get() = "1"
    override val gpu_cap_rate: String
        get() = "396000000"
    override val boost_freq: String
        get() = "696000"
    override val boost_time: String
        get() = "75"
    override val boost_emc: String
        get() = "0"
    override val boost_gpu: String
        get() = "252000"
    override val boost_cpus: String
        get() = "1"
    override val scheduler: String
        get() = noop
    override val governor: String
        get() = intelliactive
    override val max_cpu_online: String
        get() = "2"
    override val min_cpu_online: String
        get() = "1"
    override val min_sample_time: String
        get() = "10000"
    override val timer_rate: String
        get() = "30000"
    override val sampling_down_factor: String
        get() = "1"
    override val above_hispeed_delay: String
        get() = "40000 564000:10000 696000:5000"
    override val io_is_busy: String
        get() = "0"
    override val sync_freq: String
        get() = "564000"
    override val up_threshold_any_cpu_load: String
        get() = "80"
    override val up_threshold_any_cpu_freq: String
        get() = "1044000"
    override val hispeed_freq: String
        get() = "828000"
    override val target_loads: String
        get() = "95"
}