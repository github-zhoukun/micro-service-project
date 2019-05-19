package com.zk.elasticsearchlog.db.model;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.util.Date;

/**
 * 订单
 * retail_b2c_order
 */
@Data
@Alias("RetailB2cOrderDo")
public class RetailB2cOrderDo {
    private Integer order_id;

    private String order_code;

    private String out_code;

    private Integer order_status;

    private String seller_shop_code;

    private String seller_shop_name;

    private String seller_shop_address;

    private String operator_id;

    private String operator_name;

    private String goods_spu_id;

    private String goods_sku_id;

    private String model_code;

    private String model_name;

    private Long earnest;

    private Long already_paid;

    private Long retainage;

    private String downPayment;

    private Long retainage_already_pay;

    private Long retrofit_price;

    private Long naked_car_price;

    private String mortgage_periods;

    private Long mortgage_money;

    private String mortgage_method;

    private String pay_method;

    private Long deal_price;

    private Date make_get_car_date;

    private Date buyer_earnest_expiry_date;

    private Date buyer_earnest_pay_date;

    private Date retainage_pay_date;

    private String sales_id;

    private String sales_name;

    private String sales_phone;

    private Integer is_sync;

    private String buyer_type;

    private String customer_id;

    private String payer_id;

    private String payer_name;

    private String payer_phone;

    private String payer_identity;

    private String plate_user_id;

    private String plate_user_type;

    private String plate_user_name;

    private String plate_user_phone;

    private String plate_user_identity;

    private String plate_company_social_unicode;

    private String plate_company_name;

    private String plate_company_contact_user;

    private String plate_company_contact_phone;

    private String plate_province_code;

    private String plate_province_name;

    private String plate_city_code;

    private String plate_city_name;

    private Integer client_type_code;

    private String client_province_code;

    private String client_province_name;

    private String client_city_code;

    private String client_city_name;

    private String client_district_code;

    private String client_district_name;

    private String client_address;

    private Date carry_car_date;

    private String identity_frontage;

    private Date identity_frontage_upload_date;

    private String identity_opposite;

    private Date identity_opposite_upload_date;

    private String store_receipt;

    private Date store_receipt_upload_date;

    private String purchase_tax_certificate;

    private Date purchase_tax_certificate_upload_date;

    private String driving_license;

    private Date driving_license_upload_date;

    private String car_register_certificate;

    private Date car_register_certificate_upload_date;

    private String business_license;

    private Date business_license_upload_date;

    private String rights_obligations_transfer_aggrement;

    private Date rights_obligations_transfer_aggrement_upload_date;

    private Date make_invoice_date;

    private String alipay_scan_user;

    private String supplementary_provisions;

    private String vin_number;

    private String remark;

    private String manufactor_id;

    private Long voucher_price;

    private String ma_code;

    private String tmall_source_order;

    private String tmall_scan_user;

    private String order_source;

    private String seller_site_id;

    private Date date_create;

    private Date date_update;

    private Integer deleted;

    private String test_flag;

    private Integer used_car_exchange;

    private String nature;

    private String car_usage;

    private Integer oushang_paied_earnest;

    private Integer order_audit_status;

    private Integer contract_audit_status;

    private Integer finance_audit_status;

    private Integer funding_plan;

    private Long retainage_pay_offline;

    private String sap_fail_msg;

    private String huitong_apply_label;

    private String group_id;

    private String plate_user_credentials_type;

    private Integer retail_order_type;

    private String flagship_store_id;

    private String flagship_store_name;

    private Date pickup_at;

    private Integer business_model;

    private String down_payment_ratio;

    private Integer loan_method;

    private Integer pangolin;

    private String increased_type;

    private Long increased_price;

    private String sales_type;

}
