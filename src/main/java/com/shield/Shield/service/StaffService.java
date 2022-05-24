package com.shield.Shield.service;

import com.shield.Shield.entity.Staff;

public interface StaffService {
    Staff addDetails(Staff staff);

    Staff getCompanyDataFromToken ();
}
