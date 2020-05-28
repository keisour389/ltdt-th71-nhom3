package com.example.shippers.Common;

public class Account {
    public String getAccountTypeById(int id)
    {
        String result = "";
        switch (id)
        {
            case 1:
                result = "Cus";
                break;
            case 2:
                result = "Shi";
                break;
        }
        return result;
    }
}
