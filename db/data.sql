
USE art_devs;
GO 

INSERT INTO dbo.role
        ( role_name )
VALUES  ( N'admin'  -- role_name - nvarchar(255)
        ),
		( N'user'  -- role_name - nvarchar(255)
        ),
		( N'shipper'  -- role_name - nvarchar(255)
        ); 
GO

INSERT INTO dbo.payment_method
        ( payment_name )
VALUES  ( N'Thanh toán khi nhận hàng'  -- payment_name - nvarchar(255)
        ),
		( N'VNPAY'  -- payment_name - nvarchar(255)
        );
GO

INSERT INTO dbo.account
        ( status ,
          email ,
          fullname ,
          image ,
          password ,
          account_id,
		  account_with_google
        )
VALUES  ( 0 , -- is_del - bit
          'linhptpc04737@fpt.edu.vn' , -- email - varchar(255)
          N'Phan Tuấn Linh' , -- fullname - nvarchar(255)
          'linhptpc04737.png' , -- image - varchar(255)
          '$2a$10$kpnU5NRvBiGYfLoH.GuQ5uUFHx6M37QuihnsfN1z60VqCzX24HFZK' , -- password đã được mã hóa 
          'linhptpc04737',  -- user_id - varchar(255)
		  0
        ),
		( 0 , -- is_del - bit
          'nguyentcpc04750@fpt.edu.vn' , -- email - varchar(255)
          N'Trần Chí Nguyễn' , -- fullname - nvarchar(255)
          'nguyentcpc04750.png' , -- image - varchar(255)
          '$2a$10$kpnU5NRvBiGYfLoH.GuQ5uUFHx6M37QuihnsfN1z60VqCzX24HFZK' , -- password đã được mã hóa 
          'nguyentcpc04750'  ,  -- user_id - varchar(255)
		  0
        ),
		( 0 , -- is_del - bit
          'vinhtppc04838@fpt.edu.vn' , -- email - varchar(255)
          N'Trần Phúc Vinh' , -- fullname - nvarchar(255)
          'vinhtppc04838.png' , -- image - varchar(255)
          '$2a$10$kpnU5NRvBiGYfLoH.GuQ5uUFHx6M37QuihnsfN1z60VqCzX24HFZK' , -- password đã được mã hóa 
          'vinhtppc04838' ,  -- user_id - varchar(255)
		  0
        ),
		( 0 , -- is_del - bit
          'nguyentc@fpt.edu.vn' , -- email - varchar(255)
          N'Trần Chí Nguyễn' , -- fullname - nvarchar(255)
          'nguyentc.png' , -- image - varchar(255)
          '$2a$10$kpnU5NRvBiGYfLoH.GuQ5uUFHx6M37QuihnsfN1z60VqCzX24HFZK' , -- password đã được mã hóa 
          'nguyentc' ,  -- user_id - varchar(255)
		  0
        );
GO

INSERT INTO dbo.account_role
        ( role_id, account_id )
VALUES  ( 1, -- role_id - int
        'vinhtppc04838'  -- user_id - varchar(255)
        ),
		( 2, -- role_id - int
        'vinhtppc04838'  -- user_id - varchar(255)
        ),
		( 1, -- role_id - int
        'nguyentcpc04750'  -- user_id - varchar(255)
        ),
		( 2, -- role_id - int
        'nguyentcpc04750'  -- user_id - varchar(255)
        ),
		( 3, -- role_id - int
        'nguyentcpc04750'  -- user_id - varchar(255)
        ),
		( 2, -- role_id - int
        'linhptpc04737'  -- user_id - varchar(255)
        ),
		( 3, -- role_id - int
        'nguyentc'  -- user_id - varchar(255)
        );
GO 

INSERT INTO dbo.infor_address
        ( city ,
          district ,
          phone_number ,
          specific ,
          user_infor ,
          ward,
		  nickname
        )
VALUES  ( N'Cần Thơ' , -- city - nvarchar(255)
          N'Ninh Kiều', -- district - nvarchar(255)
          '0948922927' , -- phone_number - varchar(255)
          N'Đường Trần Hưng Đạo' , -- specific - nvarchar(255)
          'linhptpc04737' , -- user_infor - varchar(255)
          N'An phú',  -- ward - nvarchar(255)
		  N'Baba'
        ),
		( N'Cần Thơ' , -- city - nvarchar(255) 
          N'Cờ Đỏ', -- district - nvarchar(255)
          '0909123123' , -- phone_number - varchar(255)
          N'' , -- specific - nvarchar(255)
          'linhptpc04737' , -- user_infor - varchar(255)
          N'Thới Hưng',  -- ward - nvarchar(255)
		  N'Baba'
        ),
		( N'Sóc Trăng' , -- city - nvarchar(255)
          N'Mỹ Tú', -- district - nvarchar(255)
          '0911726601' , -- phone_number - varchar(255)
          N'' , -- specific - nvarchar(255)
          'nguyentcpc04750' , -- user_infor - varchar(255)
          N'Hưng Phú',  -- ward - nvarchar(255)
		  N'Baba'
        ),
		( N'Cần Thơ' , -- city - nvarchar(255)
          N'Cờ Đỏ', -- district - nvarchar(255)
          '0909888666' , -- phone_number - varchar(255)
          N'' , -- specific - nvarchar(255)
          'vinhtppc04838' , -- user_infor - varchar(255)
          N'Thới Hưng',  -- ward - nvarchar(255)
		  N'Baba'
        );
GO

INSERT INTO dbo.keyword_suggestions
        ( date, keywords, user_id )
VALUES  ( SYSDATETIME(), -- date - datetime2(6)
          N'Ti vi Samsung', -- keywords - nvarchar(255)
          'linhptpc04737'  -- user_id - varchar(255)
          ),
		  ( SYSDATETIME(), -- date - datetime2(6)
          N'Máy lạnh', -- keywords - nvarchar(255)
          'linhptpc04737'  -- user_id - varchar(255)
          ),
		  ( SYSDATETIME(), -- date - datetime2(6)
          N'Nồi chiên', -- keywords - nvarchar(255)
          'linhptpc04737'  -- user_id - varchar(255)
          ),
		  ( SYSDATETIME(), -- date - datetime2(6)
          N'Tủ', -- keywords - nvarchar(255)
          'linhptpc04737'  -- user_id - varchar(255)
          ),
		  ( SYSDATETIME(), -- date - datetime2(6)
          N'Bếp gas', -- keywords - nvarchar(255)
          'linhptpc04737'  -- user_id - varchar(255)
          );
GO

INSERT INTO dbo.voucher
         (maximum_number_of_uses ,
          discount ,
          number_of_uses ,
          start_day ,
          end_day ,
          user_voucher
        )
VALUES  ( 
          100 , -- maximum_number_of_uses - int
          99000 , -- maximum_price_discount - float
          100 , -- number_of_uses - int
          SYSDATETIME() ,   -- end_day - datetime2(6)
          '2023-12-31 23:59:00' , -- start_day - datetime2(6)
          'nguyentcpc04750'  -- user_voucher - varchar(255)
        ),
        ( 
          100 , -- maximum_number_of_uses - int
          49000 , -- maximum_price_discount - float
          20 , -- number_of_uses - int
          SYSDATETIME() ,   -- end_day - datetime2(6)
          '2023-12-31 23:59:00' , -- start_day - datetime2(6)
          'nguyentcpc04750'  -- user_voucher - varchar(255)
        ),
        ( 
          100 , -- maximum_number_of_uses - int
          39000 , -- maximum_price_discount - float
          10 , -- number_of_uses - int
          SYSDATETIME() ,   -- end_day - datetime2(6)
          '2023-12-31 23:59:00' , -- start_day - datetime2(6)
          'nguyentcpc04750'  -- user_voucher - varchar(255)
        ),
        ( 
          100 , -- maximum_number_of_uses - int
          29000 , -- maximum_price_discount - float
          20 , -- number_of_uses - int
          SYSDATETIME() ,   -- end_day - datetime2(6)
          '2023-12-31 23:59:00' , -- start_day - datetime2(6)
          'nguyentcpc04750'  -- user_voucher - varchar(255)
        ),
        ( 
          100 , -- maximum_number_of_uses - int
          19000 , -- maximum_price_discount - float
          18 , -- number_of_uses - int
          SYSDATETIME() ,   -- end_day - datetime2(6)
          '2023-12-31 23:59:00' , -- start_day - datetime2(6)
          'nguyentcpc04750'  -- user_voucher - varchar(255)
        ),
        ( 
          300 , -- maximum_number_of_uses - int
          10000 , -- maximum_price_discount - float
          25 , -- number_of_uses - int
          SYSDATETIME() ,   -- end_day - datetime2(6)
          '2023-12-31 23:59:00' , -- start_day - datetime2(6)
          'nguyentcpc04750'  -- user_voucher - varchar(255)
        );
GO

INSERT INTO dbo.flash_sale
        ( status ,
          start_day ,
          end_day ,
          user_flash_sale
        )
VALUES  ( 0 , -- is_status - bit
          '2023-01-01 00:00:00' ,  -- start_day - datetime2(6)
          '2023-02-01 23:59:00' ,  -- end_day - datetime2(6)
          'nguyentcpc04750'  -- user_id - varchar(255)
        ),
        ( 0 , -- is_status - bit
          '2023-02-01 00:00:00' ,  -- start_day - datetime2(6)
          '2023-03-01 23:59:00' ,  -- end_day - datetime2(6)
          'nguyentcpc04750'  -- user_id - varchar(255)
        ),
        ( 1 , -- is_status - bit
          '2023-12-01 00:00:00' ,  -- start_day - datetime2(6)
          '2024-02-01 23:59:00' ,  -- end_day - datetime2(6)
          'nguyentcpc04750'  -- user_id - varchar(255)
        ),
        ( 0 , -- is_status - bit
          '2023-04-01 00:00:00' ,  -- start_day - datetime2(6)
          '2023-05-01 23:59:00' ,-- end_day - datetime2(6)
          'nguyentcpc04750'  -- user_id - varchar(255)
        ),
        ( 0 , -- is_status - bit
          '2023-05-01 00:00:00' ,  -- start_day - datetime2(6)
          '2023-06-01 23:59:00' ,-- end_day - datetime2(6)
          'nguyentcpc04750'  -- user_id - varchar(255)
        ),
        ( 0 , -- is_status - bit
          '2023-06-01 00:00:00' ,  -- start_day - datetime2(6)
          '2023-07-01 23:59:00' ,-- end_day - datetime2(6)
          'nguyentcpc04750'  -- user_id - varchar(255)
        ),
        ( 0 , -- is_status - bit
          '2023-07-01 00:00:00' ,  -- start_day - datetime2(6)
          '2023-08-01 23:59:00' ,-- end_day - datetime2(6)
          'nguyentcpc04750'  -- user_id - varchar(255)
        ),
        ( 0 , -- is_status - bit
          '2023-08-01 00:00:00' ,  -- start_day - datetime2(6)
          '2023-09-01 23:59:00' ,-- end_day - datetime2(6)
          'nguyentcpc04750'  -- user_id - varchar(255)
        ),
        ( 0 , -- is_status - bit
          '2023-09-01 00:00:00' ,  -- start_day - datetime2(6)
          '2023-10-01 23:59:00' ,-- end_day - datetime2(6)
          'nguyentcpc04750'  -- user_id - varchar(255)
        ),
        ( 0 , -- is_status - bit
          '2023-10-01 00:00:00' ,  -- start_day - datetime2(6)
          '2023-11-01 23:59:00' ,-- end_day - datetime2(6)
          'nguyentcpc04750'  -- user_id - varchar(255)
        ),
        ( 0 , -- is_status - bit
          '2023-11-01 00:00:00' ,  -- start_day - datetime2(6)
          '2023-12-01 23:59:00' ,  -- end_day - datetime2(6)
          'nguyentcpc04750'  -- user_id - varchar(255)
        );
GO


INSERT  INTO dbo.banner
        ( banner_name, user_banner )
VALUES  ( 'salemonth09.png', -- banner_name - varchar(255)
          'nguyentcpc04750'  -- user_id - varchar(255)
        ),
        ( 'salemonth10.png', -- banner_name - varchar(255)
          'nguyentcpc04750'  -- user_id - varchar(255)
        ),
        ( 'salemonth11.png', -- banner_name - varchar(255)
          'nguyentcpc04750'  -- user_id - varchar(255)
        );
GO


INSERT INTO dbo.manufacturer
        ( del ,
          manufacturer_name ,
          user_manufacturer
        )
VALUES  ( 0 , -- is_del - bit
          'Samsung' , -- manufacturer_name - varchar(255)
          'nguyentcpc04750'  -- user_id - varchar(255)
        ),
		( 0 , -- is_del - bit
          'LG' , -- manufacturer_name - varchar(255)
          'nguyentcpc04750'  -- user_id - varchar(255)
        ),
		( 0 , -- is_del - bit
          'Toshiba' , -- manufacturer_name - varchar(255)
          'nguyentcpc04750'  -- user_id - varchar(255)
        ),
		( 0 , -- is_del - bit
          'Asanyo' , -- manufacturer_name - varchar(255)
          'nguyentcpc04750'  -- user_id - varchar(255)
        ),
		( 0 , -- is_del - bit
          'Media' , -- manufacturer_name - varchar(255)
          'nguyentcpc04750'  -- user_id - varchar(255)
        ),
		( 0 , -- is_del - bit
          'VSP' , -- manufacturer_name - varchar(255)
          'nguyentcpc04750'  -- user_id - varchar(255)
        ),
		( 0 , -- is_del - bit
          'Sony' , -- manufacturer_name - varchar(255)
          'nguyentcpc04750'  -- user_id - varchar(255)
        ),
		( 0 , -- is_del - bit
          'TCL' , -- manufacturer_name - varchar(255)
          'nguyentcpc04750'  -- user_id - varchar(255)
        ),
		( 1 , -- available - bit
          'Sharp' , -- manufacturer_name - varchar(255)
          'nguyentcpc04750'  -- user_id - varchar(255)
        ),
		( 0 , -- is_del - bit
          'Aqua' , -- manufacturer_name - varchar(255)
          'nguyentcpc04750'  -- user_id - varchar(255)
        ),
		( 0 , -- is_del - bit
          'Xiaomi' , -- manufacturer_name - varchar(255)
          'nguyentcpc04750'  -- user_id - varchar(255)
        );
GO

INSERT INTO dbo.category
        ( status, category_name, user_category )
VALUES  ( 1, -- is_del - bit
          N'Tivi', -- category_name - nvarchar(255)
          'nguyentcpc04750'  -- user_id - varchar(255)
        ),
		( 1, -- is_del - bit
          N'Tủ lạnh', -- category_name - nvarchar(255)
          'nguyentcpc04750'  -- user_id - varchar(255)
        ),
		( 1, -- is_del - bit
         N'Máy giặt', -- category_name - nvarchar(255)
          'nguyentcpc04750'  -- user_id - varchar(255)
        ),
		( 1, -- is_del - bit
          N'Máy điều hòa', -- category_name - nvarchar(255)
          'nguyentcpc04750'  -- user_id - varchar(255)
        ),
		( 1, -- is_del - bit
          N'Bếp điện', -- category_name - nvarchar(255)
          'nguyentcpc04750'  -- user_id - varchar(255)
        ),
		( 0, -- is_del - bit
          N'Bếp gas', -- category_name - nvarchar(255)
          'nguyentcpc04750'  -- user_id - varchar(255)
        ),
		( 0, -- is_del - bit
          N'Máy sấy', -- category_name - nvarchar(255)
          'nguyentcpc04750'  -- user_id - varchar(255)
        ),
		( 0, -- is_del - bit
          N'Tủ đông', -- category_name - nvarchar(255)
          'nguyentcpc04750'  -- user_id - varchar(255)
        ),
		( 0, -- is_del - bit
          N'Nồi cơm', -- category_name - nvarchar(255)
          'nguyentcpc04750'  -- user_id - varchar(255)
        ),
		( 0, -- is_del - bit
          N'Nồi chiên', -- category_name - nvarchar(255)
          'nguyentcpc04750'  -- user_id - varchar(255)
        );
GO

INSERT INTO dbo.product
        ( category ,
          available ,
          manufacturer , 
          product_id, 
          product_name ,
          user_product,
          created_date
        )
VALUES  ( 1 , -- category_id - int
          1 , -- available - bit
          1 , -- manufacturer_id - int
          'QA55Q65A' , -- product_id - varchar(255)
          N'Smart Tivi QLED 4K 55 inch Samsung QA55Q65A' , -- product_name - nvarchar(255)
          'nguyentcpc04750',  -- user_id - varchar(255)
          SYSDATETIME()
        ),
        ( 1 , -- category_id - int
          1 , -- available - bit
          2 , -- manufacturer_id - int
          '55NANO76SQA' , -- product_id - varchar(255)
          N'Smart Tivi NanoCell LG 4K 55 inch 55NANO76SQA' , -- product_name - nvarchar(255)
          'nguyentcpc04750',  -- user_id - varchar(255)
          SYSDATETIME()
        ),
        ( 1 , -- category_id - int
          1 , -- available - bit
          3 , -- manufacturer_id - int
          '32V35KP' , -- product_id - varchar(255)
          N'Android Tivi Toshiba 32 inch 32V35KP' , -- product_name - nvarchar(255)
          'nguyentcpc04750',  -- user_id - varchar(255)
          SYSDATETIME()
        ),
        ( 1 , -- category_id - int
          1 , -- available - bit
          1 , -- manufacturer_id - int
          'QA43Q65A' , -- product_id - varchar(255)
          N'Smart Tivi QLED 4K 43 inch Samsung QA43Q65A' , -- product_name - nvarchar(255)
          'nguyentcpc04750',  -- user_id - varchar(255)
          SYSDATETIME()
        ),
		( 1 , -- category_id - int
          1 , -- available - bit
          1 , -- manufacturer_id - int
          'UA43BU8000' , -- product_id - varchar(255)
          N'Smart Tivi Samsung 4K Crystal UHD 43 inch UA43BU8000' , -- product_name - nvarchar(255)
          'nguyentcpc04750',  -- user_id - varchar(255)
          SYSDATETIME()
        ),
		    ( 1 , -- category_id - int
          1 , -- available - bit
          1 , -- manufacturer_id - int
          'UA55AU8100' , -- product_id - varchar(255)
          N'Smart Tivi Samsung 4K Crystal UHD 55 inch UA55AU8100' , -- product_name - nvarchar(255)
          'nguyentcpc04750',  -- user_id - varchar(255)
          SYSDATETIME()
        ),
		    ( 1 , -- category_id - int
          1 , -- available - bit
          1 , -- manufacturer_id - int
          'UA43AU7002' , -- product_id - varchar(255)
          N'Smart Tivi Samsung 4K 43 inch UA43AU7002' , -- product_name - nvarchar(255)
          'nguyentcpc04750',  -- user_id - varchar(255)
          SYSDATETIME()
        ),
		    ( 1 , -- category_id - int
          1 , -- available - bit
          1 , -- manufacturer_id - int
          'UA65AU8100' , -- product_id - varchar(255)
          N'Smart Tivi Samsung 4K Crystal UHD 65 inch UA65AU8100' , -- product_name - nvarchar(255)
          'nguyentcpc04750',  -- user_id - varchar(255)
          SYSDATETIME()
        ),
        ( 1 , -- category_id - int
          1 , -- available - bit
          1 , -- manufacturer_id - int
          'QA65Q65A' , -- product_id - varchar(255)
          N'Smart Tivi QLED 4K 65 inch Samsung QA65Q65A' , -- product_name - nvarchar(255)
          'nguyentcpc04750',  -- user_id - varchar(255)
          SYSDATETIME()
        ),
		( 1 , -- category_id - int
          1 , -- available - bit
          1 , -- manufacturer_id - int
          'QA50Q65A' , -- product_id - varchar(255)
          N'Smart Tivi QLED 4K 50 inch Samsung QA50Q65A' , -- product_name - nvarchar(255)
          'nguyentcpc04750',  -- user_id - varchar(255)
          SYSDATETIME()
        ),
		( 1 , -- category_id - int
          1 , -- available - bit
          1 , -- manufacturer_id - int
          'UA55BU8000' , -- product_id - varchar(255)
          N'Smart Tivi Samsung 4K Crystal UHD 55 inch UA55BU8000' , -- product_name - nvarchar(255)
          'nguyentcpc04750',  -- user_id - varchar(255)
          SYSDATETIME()
        ),
		( 1 , -- category_id - int
          1 , -- available - bit
          1 , -- manufacturer_id - int
          'QA65Q60B' , -- product_id - varchar(255)
          N'Smart Tivi QLED 4K 65 inch Samsung QA65Q60B' , -- product_name - nvarchar(255)
          'nguyentcpc04750',  -- user_id - varchar(255)
          SYSDATETIME()
        ),
		( 1 , -- category_id - int
          1 , -- available - bit
          2 , -- manufacturer_id - int
          '55UQ8000PSC' , -- product_id - varchar(255)
          N'Smart Tivi LG 4K 55 inch 55UQ8000PSC' , -- product_name - nvarchar(255)
          'nguyentcpc04750',  -- user_id - varchar(255)
          SYSDATETIME()
        ),
		( 1 , -- category_id - int
          1 , -- available - bit
          2 , -- manufacturer_id - int
          '65NANO76SQA' , -- product_id - varchar(255)
          N'Smart Tivi NanoCell LG 4K 65 inch 65NANO76SQA' , -- product_name - nvarchar(255)
          'nguyentcpc04750',  -- user_id - varchar(255)
          SYSDATETIME()
        ),
        ( 1 , -- category_id - int
          1 , -- available - bit
          2 , -- manufacturer_id - int
          '65UQ8000PSC' , -- product_id - varchar(255)
          N'Smart Tivi LG 4K 65 inch 65UQ8000PSC' , -- product_name - nvarchar(255)
          'nguyentcpc04750',  -- user_id - varchar(255)
          SYSDATETIME()
        ),( 1 , -- category_id - int
          1 , -- available - bit
          2 , -- manufacturer_id - int
          '43UQ7550PSF' , -- product_id - varchar(255)
          N'Smart Tivi LG 4K 43 inch 43UQ7550PSF' , -- product_name - nvarchar(255)
          'nguyentcpc04750',  -- user_id - varchar(255)
          SYSDATETIME()
        ),
        ( 1 , -- category_id - int
          1 , -- available - bit
          2 , -- manufacturer_id - int
          '43UQ8000PSC' , -- product_id - varchar(255)
          N'Smart Tivi LG 4K 43 inch 43UQ8000PSC' , -- product_name - nvarchar(255)
          'nguyentcpc04750',  -- user_id - varchar(255)
          SYSDATETIME()
        ),
        ( 1 , -- category_id - int
          1 , -- available - bit
          2 , -- manufacturer_id - int
          '43NANO76SQA' , -- product_id - varchar(255)
          N'Smart Tivi NanoCell LG 4K 43 inch 43NANO76SQA' , -- product_name - nvarchar(255)
          'nguyentcpc04750',  -- user_id - varchar(255)
          SYSDATETIME()
        ),
        ( 1 , -- category_id - int
          1 , -- available - bit
          2 , -- manufacturer_id - int
          '50UQ7550PSF' , -- product_id - varchar(255)
          N'Smart Tivi LG 4K 50 inch 50UQ7550PSF' , -- product_name - nvarchar(255)
          'nguyentcpc04750',  -- user_id - varchar(255)
          SYSDATETIME()
        ),
        ( 1 , -- category_id - int
          1 , -- available - bit
          2 , -- manufacturer_id - int
          '50UQ8000PSC' , -- product_id - varchar(255)
          N'Smart Tivi LG 4K 50 inch 50UQ8000PSC' , -- product_name - nvarchar(255)
          'nguyentcpc04750',  -- user_id - varchar(255)
          SYSDATETIME()
        ),
        ( 1 , -- category_id - int
          1 , -- available - bit
          2 , -- manufacturer_id - int
          '55UQ7550PSF' , -- product_id - varchar(255)
          N'Smart Tivi LG 4K 55 inch 55UQ7550PSF' , -- product_name - nvarchar(255)
          'nguyentcpc04750',  -- user_id - varchar(255)
          SYSDATETIME()
        ),
        ( 2 , -- category_id - int
          1 , -- available - bit
          1 , -- manufacturer_id - int
          'RT25M4032BU' , -- product_id - varchar(255)
          N'Tủ lạnh Samsung Inverter 256 lít RT25M4032BU' , -- product_name - nvarchar(255)
          'nguyentcpc04750',  -- user_id - varchar(255)
          SYSDATETIME()
        ),
        ( 2 , -- category_id - int
          1 , -- available - bit
          2 , -- manufacturer_id - int
          'GR-B256JDS' , -- product_id - varchar(255)
          N'Tủ lạnh LG Inverter 519 lít Side By Side GR-B256JDS' , -- product_name - nvarchar(255)
          'nguyentcpc04750',  -- user_id - varchar(255)
          SYSDATETIME()
        ),
        ( 2 , -- category_id - int
          1 , -- available - bit
          3 , -- manufacturer_id - int
          'GR-RF610WE-PGV(22)-XK' , -- product_id - varchar(255)
          N'Tủ lạnh Toshiba Inverter 511 lít Multi Door GR-RF610WE-PGV(22)-XK' , -- product_name - nvarchar(255)
          'nguyentcpc04750',  -- user_id - varchar(255)
          SYSDATETIME()
        ),
        ( 3 , -- category_id - int
          1 , -- available - bit
          1 , -- manufacturer_id - int
          'WW80T3020WW' , -- product_id - varchar(255)
          N'Máy giặt Samsung Inverter 8kg WW80T3020WW' , -- product_name - nvarchar(255)
          'nguyentcpc04750',  -- user_id - varchar(255)
          SYSDATETIME()
        ),
        ( 3 , -- category_id - int
          1 , -- available - bit
          2 , -- manufacturer_id - int
          'T2351VSAB' , -- product_id - varchar(255)
          N'Máy giặt LG TurboDrum Inverter 11.5 kg T2351VSAB' , -- product_name - nvarchar(255)
          'nguyentcpc04750',  -- user_id - varchar(255)
          SYSDATETIME()
        ),
        ( 3 , -- category_id - int
          1 , -- available - bit
          3 , -- manufacturer_id - int
          'AW-L805AV' , -- product_id - varchar(255)
          N'Máy giặt Toshiba 7 Kg AW-L805AV (SG)' , -- product_name - nvarchar(255)
          'nguyentcpc04750',  -- user_id - varchar(255)
          SYSDATETIME()
        ),
        ( 4 , -- category_id - int
          1 , -- available - bit
          1 , -- manufacturer_id - int
          'AR13CYFAAWKNSV' , -- product_id - varchar(255)
          N'Máy lạnh Samsung Inverter 1.5 HP AR13CYFAAWKNSV' , -- product_name - nvarchar(255)
          'nguyentcpc04750',  -- user_id - varchar(255)
          SYSDATETIME()
        ),
        ( 4 , -- category_id - int
          1 , -- available - bit
          2 , -- manufacturer_id - intl (18,2) - Giá sản phẩm
         'ZTNQ18GPLA0' , -- product_id - varchar(255)
          N'Máy lạnh âm trần LG Inverter 2 HP ZTNQ18GPLA0' , -- product_name - nvarchar(255)
          'nguyentcpc04750',  -- user_id - varchar(255)
          SYSDATETIME()
        ),
        ( 4 , -- category_id - int
          1 , -- available - bit
          3 , -- manufacturer_id - int
          'RAS-H10Z1KCVG-V' , -- product_id - varchar(255)
          N'Máy lạnh Toshiba Inverter 1 HP RAS-H10Z1KCVG-V' , -- product_name - nvarchar(255)
          'nguyentcpc04750',  -- user_id - varchar(255)
          SYSDATETIME()
        ),
        ( 4 , -- category_id - int
          1 , -- available - bit
          5 , -- manufacturer_id - int
          'MSAGA-10CRDN8' , -- product_id - varchar(255)
          N'Máy lạnh Midea Inverter 1 HP MSAGA-10CRDN8' , -- product_name - nvarchar(255)
          'nguyentcpc04750',  -- user_id - varchar(255)
          SYSDATETIME()
        );

GO
	
INSERT INTO dbo.detail_description
        ( description, product_id, title )
VALUES  ( N'Smart Tivi QLED 4K 55 inch Samsung QA55Q65A với thiết kế với màn hình tràn viền 4 cạnh, 
			cho người dùng trải nghiệm khung hình giải trí trên tivi chân thực như thực tế đang xảy ra trước mắt.
			Tivi Samsung 55 inch có chân đế được thiết kế gọn gàng, vững chắc. Mang lại sự thanh lịch, 
			sang trọng cho chiếc tivi, phù hợp trưng bày ở phòng khách, phòng ngủ,..', -- description - nvarchar(255)
          'QA55Q65A', -- product_id - varchar(255)
          N'Thiết kế thanh mảnh, màn hình tràn viền 4 cạnh ấn tượng'  -- tile - nvarchar(255)
          ),
		  ( N'Smart Tivi NanoCell LG 4K 55 inch 55NANO76SQA sở hữu màn hình 55 inch cùng thiết kế thanh mảnh, 
			tinh tế, bố trí sang đẹp cho phòng khách gia đình, phù hợp cho cả phòng ngủ, phòng họp nhỏ, phòng khách sạn,...
			Chân đế hình bán nguyệt cấu tạo vỏ nhựa lõi kim loại chắc chắn, bố trí lắp để bàn đẹp mắt, mềm mại, 
			dễ dàng tháo rời để sử dụng tivi treo tường tiết kiệm không gian.', -- description - nvarchar(255)
          '55NANO76SQA', -- product_id - varchar(255)
          N'Tổng quan thiết kế'  -- tile - nvarchar(255)
          ),
		  ( N'Tivi Toshiba này có thiết kế nhỏ gọn cùng tone màu đen sang trọng, thanh lịch, tạo điểm nhấn cho không gian nhà.
			Màn hình 32 inch phù hợp cho những không gian phòng khách, phòng ngủ nhỏ. 
			Chân đế được làm bằng nhựa chắc chắn, thiết kế dạng chữ V úp ngược giúp tivi có thể đứng vững trên bàn hay kệ tủ. 
			Tivi còn có thể treo tường hiện đại, giúp tiết kiệm được không gian. ', -- description - nvarchar(255)
          '32V35KP', -- product_id - varchar(255)
          N'Tổng quan thiết kế'  -- tile - nvarchar(255)
          ),
		  ( N'Tủ lạnh Samsung Inverter 256 lít RT25M4032BU được trang bị ngăn Đông Mềm Optimal Fresh Zone 
			có tác dụng giữ thực phẩm tươi sống còn trọn dưỡng chất, hoàn toàn không đông đá ở nhiệt độ đông mềm lý tưởng -1°C. 
			Như thế, thịt cá hoàn toàn không bị đông đá giúp bạn có thể nấu ăn ngay không cần rã đông, dễ dàng cắt thái và chế biến trong ngày.
			Lưu ý, chỉ nên sử dụng ngăn đông mềm này với các thực phẩm muốn chế biến trong ngày, nếu muốn trữ lâu hơn, 
			bạn nên dùng ngăn đông để bảo quản tốt nhất.', -- description - nvarchar(255)
          'RT25M4032BU', -- product_id - varchar(255)
          N'Bảo quản thực phẩm tươi sống, sử dụng trong ngày không cần rã đông với Ngăn đông mềm -1 độ C Optimal Fresh Zone'  -- tile - nvarchar(255)
          ),
		  ( N'- Dòng tủ lạnh side by side, chất liệu cửa tủ được làm từ thép không gỉ bền chắc với màu bạc tinh tế, 
			hòa hợp với mọi không gian nội thất.
			Tay nắm cửa được thiết kế âm tủ giúp tổng thể liền mạch, đem đến sự tối giản, hiện đại.
			Tủ lạnh sẽ thích hợp đặt trong phòng khách và nhà bếp.
			Dung tích 519 lít thích hợp với gia đình từ 4 - 5 thành viên.', -- description - nvarchar(255)
          'GR-B256JDS', -- product_id - varchar(255)
          N'Tổng quan thiết kế'  -- tile - nvarchar(255)
          ),
		  ( N'Tủ lạnh Toshiba Inverter 511 lít Multi Door GR-RF610WE-PGV(22)-XK thiết kế tủ hiện đại với bề mặt gương sang trọng
		   cùng gam màu đen ấn tượng, là điểm nhấn trong không gian sống của gia đình. Tủ lạnh trang bị công nghệ Dual Cooling hai dàn lạnh độc lập
		   làm lạnh hiệu quả cùng công nghệ Origin Inverter tiết kiệm điện năng, vận hành êm ái. 
		   Có ngăn cấp đông linh hoạt Flexible Zone giúp điều chỉnh nhiệt độ phù hợp với nước uống, thực phẩm khô và rau củ, 
		   ngăn tăng cường độ ẩm Moisture Zone giúp điều chỉnh độ ẩm thích hợp với các loại trái cây và rau củ.', -- description - nvarchar(255)
          'GR-RF610WE-PGV(22)-XK', -- product_id - varchar(255)
          N'Tổng quan thiết kế'  -- tile - nvarchar(255)
          ),
		  ( N'Máy giặt được trang bị động cơ Digital Inverter với nam châm vĩnh cửu, giảm tối đa ma sát khi máy giặt vận hành, 
		  giúp tiết kiệm điện năng, vận hành êm ái hơn so với động cơ thông thường sử dụng chổi than. Đặc biệt, 
		  động cơ Digital Inverter có độ bền vượt trội và được bảo hành đến 11 năm.', -- description - nvarchar(255)
          'WW80T3020WW', -- product_id - varchar(255)
          N'Động cơ Digital Inverter tối ưu hóa hiệu năng và lượng điện tiêu thụ'  -- tile - nvarchar(255)
          ),
		  ( N'Chiếc máy giặt LG TurboDrum Inverter 11.5 kg T2351VSAB mang gam màu xám thanh lịch, vỏ máy kim loại sơn tĩnh điện, 
			khối lượng giặt 11.5 kg phù hợp với các gia đình trên 7 thành viên. ', -- description - nvarchar(255)
          'T2351VSAB', -- product_id - varchar(255)
          N'Thiết kế cửa trên truyền thống, sơn tĩnh điện gam màu xám thanh lịch, sang trọng'  -- tile - nvarchar(255)
          ),
		  ( N'Máy giặt Toshiba 7 Kg AW-L805AV (SG) có màu xám bạc vừa toát lên vẻ sang trọng, hiện đại vừa đảm bảo vệ sinh hơn trong quá trình sử dụng. 
			Với thiết kế cửa trên, lồng đứng và có khối lượng giặt là 7 kg này sẽ phù hợp với những gia đình có 2 - 3 thành viên. 
			Hoặc với gia đình có đông thành viên hơn nhưng nhu cầu giặt giũ ít thì vẫn là lựa chọn phù hợp cho gia đình bạn.', -- description - nvarchar(255)
          'AW-L805AV', -- product_id - varchar(255)
          N'Thiết kế tối giản, sang trọng, hiện đại với khối lượng giặt 7 kg phù hợp cho gia đình 2 - 3 người'  -- tile - nvarchar(255)
          ),
		  ( N'Công suất làm lạnh 1.5 HP làm lạnh hiệu quả cho diện tích từ 15 - 20m².
			Tiết kiệm điện và đảm bảo máy vận hành êm ái nhờ công nghệ Digital Inverter Boost và Eco.
			Lọc bụi hiệu quả, dễ tháo dỡ vệ sinh với bộ lọc Copper Anti-bacteria Filter.
			Công nghệ Fast Cooling làm lạnh nhanh chóng không gian.
			Chế độ Wind-Free cho hơi lạnh tỏa ra nhẹ nhàng, tạo cảm giác dễ chịu.
			Hong khô dàn lạnh, hạn chế nấm mốc nhờ có chức năng tự làm sạch Auto Clean.
			Tiện lợi điều chỉnh nhiệt độ máy lạnh từ xa qua ứng dụng SmartThings.
			Làm lạnh thông minh AI Auto Cooling hơi lạnh tỏa ra nhanh chóng.', -- description - nvarchar(255)
          'AR13CYFAAWKNSV', -- product_id - varchar(255)
          N'Đặc điểm nổi bật'  -- tile - nvarchar(255)
          ),
		  ( N'Máy lạnh Samsung Inverter 1.5 HP AR13CYFAAWKNSV thuộc dòng máy lạnh 1 chiều sở hữu thiết kế sang trọng, 
			  làm lạnh nhanh chóng với công nghệ Fast Cooling, tạo làn gió thổi nhẹ nhàng, dễ chịu nhờ công nghệ Wind-Free, 
			  đảm bảo thiết bị vận hành êm ái, giảm tiêu thụ điện với công nghệ Digital Inverter Boost và Eco, 
			  tiết kiệm thời gian vệ sinh máy với chức năng tự làm sạch Auto Clean.', -- description - nvarchar(255)
          'AR13CYFAAWKNSV', -- product_id - varchar(255)
          N'Thông tin sản phẩm'  -- tile - nvarchar(255)
          ),
		  ( N'Máy lạnh âm trần LG Inverter 2 HP ZTNQ18GPLA0 có thiết kế âm trần phù hợp cho các khu vực đông người như văn phòng, siêu thị. 
		  Sản phẩm kèm theo nhiều tính năng như làm lạnh nhanh, Smart Inverter tiết kiệm điện và bộ lọc sơ bộ lọc bụi hiệu quả.', -- description - nvarchar(255)
          'ZTNQ18GPLA0', -- product_id - varchar(255)
          N'Thông tin sản phẩm'  -- tile - nvarchar(255)
          ),
		  ( N'Máy lạnh Toshiba Inverter 1 HP RAS-H10Z1KCVG-V làm lạnh tức thì với chế độ Hi Power, 
		  đảm bảo thiết bị hoạt động mượt mà, làm lạnh ổn định, tuổi thọ cao với công nghệ Hybrid Inverter, 
		  chế độ Eco, giữ không khí trong lành, ngăn vi khuẩn, nấm mốc phát triển với công nghệ Magic Coil, bộ lọc chống nấm mốc.', -- description - nvarchar(255)
          'RAS-H10Z1KCVG-V', -- product_id - varchar(255)
          N'Thông tin sản phẩm'  -- tile - nvarchar(255)
          ),
		  ( N'Máy lạnh Midea Inverter 1 HP MSAGA-10CRDN8 là dòng máy lạnh 1 chiều (chỉ làm lạnh), sở hữu công nghệ Inverter Quattro, 
		  chế độ iEco/Gear tiết kiệm điện năng, máy hoạt động êm ái, bền bỉ. Trang bị chế độ làm lạnh nhanh Boost, bộ lọc bụi 2 lớp HD, 
		  công nghệ lọc kép Dual Filtration làm sạch không khí, đảm bảo an toàn sức khỏe cho cả gia đình. ', -- description - nvarchar(255)
          'MSAGA-10CRDN8', -- product_id - varchar(255)
          N'Thông tin sản phẩm'  -- tile - nvarchar(255)
          ),
          ( N'Smart Tivi QLED 4K 43 inch Samsung QA43Q65A được mang lên trên mình thiết kế không viền 3 cạnh loại bỏ cảm giác hình ảnh bị giới hạn, 
          chân tivi có dạng hình chữ L đẹp mắt và vững vàng. ', -- description - nvarchar(255)
          'QA43Q65A', -- product_id - varchar(255)
          N'Thiết kế AirSlim thanh mảnh, hài hoà'  -- tile - nvarchar(255)
          ),
          ( N'Thiết kế thanh lịch với phần viền mỏng vuông vức cho tầm nhìn rộng mở, đem đến sự liền mạch cho tổng thể nội thất thiết kế.  
            Chân đế nâng đỡ được làm từ nhựa cao cấp, có thể tháo rời để treo lên tường càng làm tăng thêm vẻ sang trọng cho không gian.
            Màn hình phẳng 43 inch là lựa chọn phù hợp cho phòng họp nhỏ, phòng khách, phòng ngủ có diện tích không quá lớn,...', -- description - nvarchar(255)
          'UA43BU8000', -- product_id - varchar(255)
          N'Tổng quan thiết kế'  -- tile - nvarchar(255)
          ),
          ( N'Smart Tivi Samsung 4K 55 inch UA55AU8100 thiết kế theo phong cách AirSlim tối giản với các cạnh viền siêu mỏng tạo cảm giác màn hình không hề bị giới hạn. 
          Tivi có 2 chân đế hình chữ V úp ngược giúp trụ vững trên tất cả mặt phẳng, bạn cũng có thể treo tivi lên tường để tiết kiệm không gian.
          Màn hình tivi Samsung 55 inch phù hợp cho các căn phòng có diện tích vừa phải như phòng khách, phòng ngủ, văn phòng,...', -- description - nvarchar(255)
          'UA55AU8100', -- product_id - varchar(255)
          N'Trải nghiệm xem thêm nhập vai với màn hình 55 inch không viền 3 cạnh'  -- tile - nvarchar(255)
          ),
          ( N'Smart Tivi Samsung 4K 43 inch UA43AU7002 thiết kế tinh giản mà sang đẹp, tinh tế với màn hình phẳng viền mỏng 3 cạnh, mở rộng khung hình trải nghiệm và nâng cao thẩm mỹ của không gian lắp đặt.
            Kích thước màn hình 43 inch gọn gàng, phù hợp bố trí cho phòng khách gia đình, phòng ngủ hay phòng họp nhỏ,…
            Tivi sử dụng chân đế dạng chữ V úp ngược nâng đỡ chắc chắn màn hình, dễ dàng đặt trên kệ tủ, không gây vướng. Để tiết kiệm diện tích, bạn có thể tháo rời chân đế để lắp tivi kiểu treo tường.', -- description - nvarchar(255)
          'UA43AU7002', -- product_id - varchar(255)
          N'Tổng quan thiết kế'  -- tile - nvarchar(255)
          ),
          ( N'Smart Tivi Samsung 4K 65 inch UA65AU8100 có thiết kế Airslim không viền 3 cạnh sang trọng và tinh tế. 
          Mang lại tổng thể cho không gian trưng bày thêm điểm nhấn vô cùng ấn tượng.
          Màn hình tivi Samsung 65 inch cùng chân đế vững chắc phù hợp trưng bày các không gian như: Phòng khách, phòng ngủ, phòng họp,...', -- description - nvarchar(255)
          'UA65AU8100', -- product_id - varchar(255)
          N'Thiết kế đơn giản, viền mỏng sang trọng'  -- tile - nvarchar(255)
          ),
          ( N'Smart Tivi QLED 4K 65 inch Samsung QA65Q65A sở hữu thiết kế Airslim với viền màn hình mỏng gần như là vô hình, 
          tạo nên không gian rộng mở cho bạn như đang hòa mình vào thế giới thực trong phim khi đứng trước tivi. 
          Kích thước tivi Samsung 65 inch thích hợp đặt ở các nơi như: phòng khách, phòng họp,... 
          giúp nâng tầm không gian sống thêm đẳng cấp và sang trọng.', -- description - nvarchar(255)
          'QA65Q65A', -- product_id - varchar(255)
          N'Thiết kế Airslim thanh mảnh, hiện đại'  -- tile - nvarchar(255)
          ),
          ( N'Smart Tivi QLED 4K 50 inch Samsung QA50Q65A được thiết kế với màn hình tràn viền 4 cạnh ấn tượng, giúp người dùng có được những khung hình giải trí chân thực. 
            Tivi Samsung 50 inch được thiết kế cùng chân đế vững chắc, mang lại tổng thể tivi sang trọng và đẳng cấp, 
            tạo điểm nhấn cho không gian trưng bày như: Phòng khách, phòng ngủ,...', -- description - nvarchar(255)
          'QA50Q65A', -- product_id - varchar(255)
          N'Thiết kế sang trọng, màn hình tràn viền 4 cạnh ấn tượng'  -- tile - nvarchar(255)
          ),
          ( N'Smart Tivi Samsung 4K Crystal UHD 55 inch UA55BU8000 sở hữu thiết kế tinh tế, màn hình LED viền (Edge LED), 
          VA LCD siêu mỏng đi cùng chất lượng hình ảnh 4K cực nét, công nghệ OTS Lite điều chỉnh âm thanh theo chuyển động của vật thể, 
          hệ điều hành Tizen™ trực quan, thân thiện và vô số các tiện ích giải trí đi kèm chắc chắn đáp ứng nhu cầu giải trí cơ bản của gia đình bạn.', -- description - nvarchar(255)
          'UA55BU8000', -- product_id - varchar(255)
          N'Thông tin sản phẩm'  -- tile - nvarchar(255)
          ),
          ( N'Smart Tivi QLED 4K 65 inch Samsung QA65Q60B có thiết kế thanh mảnh, bộ xử lý Quantum 4K Lite, 
          công nghệ Object Tracking Sound Lite (OTS Lite) mang đến trải nghiệm hình ảnh và âm thanh chân thật, 
          hệ điều hành Tizen™ dễ sử dụng, Google Duo cho phép gọi video màn hình lớn cực tiện lợi.', -- description - nvarchar(255)
          'QA65Q60B', -- product_id - varchar(255)
          N'Thông tin sản phẩm'  -- tile - nvarchar(255)
          ),
          ( N'Smart Tivi LG 4K 55 inch 55UQ8000PSC tinh giản trong thiết kế thanh mảnh, sang đẹp, mang khung hình 4K rực rỡ, 
          tương phản cao ấn tượng hòa mình vào không gian nội thất hiện đại, cùng chất âm sống động tinh chỉnh qua AI Sound 
          và AI Acoustic Tuning cho người dùng trải nghiệm đầy lý thú trên từng ứng dụng giải trí từ webOS 22.', -- description - nvarchar(255)
          '55UQ8000PSC', -- product_id - varchar(255)
          N'Thông tin sản phẩm'  -- tile - nvarchar(255)
          ),
          ( N'Smart Tivi NanoCell LG 4K 65 inch 65NANO76SQA cuốn hút tầm nhìn nhờ màn hình 65 inch thanh mảnh, nội dung hiển thị sắc nét với độ phân giải 4K, tối ưu qua bộ xử lý α5 Gen5 AI 4K,
           âm thanh sống động cùng công nghệ AI Sound, kho ứng dụng webOS 22 phong phú và điều khiển đầy thông minh qua Magic Remote, AI ThinQ mang đến trải nghiệm tuyệt vời.', -- description - nvarchar(255)
          '65NANO76SQA', -- product_id - varchar(255)
          N'Thông tin sản phẩm'  -- tile - nvarchar(255)
          ),
          ( N'Smart Tivi LG 4K 65 inch 65UQ8000PSC mang đến cảm xúc đắm chìm khi trải nghiệm nghe nhìn, với khung hình 4K tương phản rực rỡ từ HDR10 Pro, 
          âm thanh chân thực tinh chỉnh theo nội dung qua AI Sound, cùng với 1 thiết kế tinh giản đầy sang chảnh, 
          và kho ứng dụng khổng lồ từ webOS 22 đáp ứng hoàn hảo nhu cầu giải trí của người dùng.', -- description - nvarchar(255)
          '65UQ8000PSC', -- product_id - varchar(255)
          N'Thông tin sản phẩm'  -- tile - nvarchar(255)
          ),
          ( N'Smart Tivi LG 4K 43 inch 43UQ7550PSF sở hữu thiết kế tinh tế, màn hình độ phân giải 4K, nâng tầm trải nghiệm xem với bộ xử lý α5 Gen5 AI 4K, hình ảnh sống động cùng công nghệ Active HDR, 
          chất âm AI Sound phù hợp với nội dung bạn đang trải nghiệm, dễ dàng tìm kiếm bằng giọng nói tiếng Việt với Magic Remote.', -- description - nvarchar(255)
          '43UQ7550PSF', -- product_id - varchar(255)
          N'Thông tin sản phẩm'  -- tile - nvarchar(255)
          ),
          ( N'Smart Tivi LG 4K 43 inch 43UQ8000PSC thanh mảnh, sang trọng, mang đến trải nghiệm nghe nhìn ấn tượng nhờ màn hình 4K sắc nét tương phản HDR10 Pro, 
          âm thanh mạnh mẽ sống động được tối ưu với AI Sound và AI Acoustic Tuning cùng kho ứng dụng webOS 22 phong phú, thỏa mãn người dùng.', -- description - nvarchar(255)
          '43UQ8000PSC', -- product_id - varchar(255)
          N'Thông tin sản phẩm'  -- tile - nvarchar(255)
          ),
          ( N'Smart Tivi NanoCell LG 4K 43 inch 43NANO76SQA nhỏ gọn, tinh tế, thể hiện khung hình 4K với dải màu rộng ấn tượng Nano Color, 
          nội dung tinh chỉnh hoàn hảo nhờ bộ xử lý α5 Gen5 AI 4K, âm thanh theo nội dung AI Sound sống động, 
          cùng trải nghiệm giải trí đầy lý thú trên kho ứng dụng webOS 22 phong phú, mang đến khung giờ giải trí đầy hứng khởi cho bạn và gia đình.', -- description - nvarchar(255)
          '43NANO76SQA', -- product_id - varchar(255)
          N'Thông tin sản phẩm'  -- tile - nvarchar(255)
          ),
          ( N'Smart Tivi LG 4K 50 inch 50UQ7550PSF tạo dấu ấn mạnh mẽ với thiết kế thanh lịch, khung hình hiển thị nội dung chất lượng 4K nhờ bộ xử lý α5 Gen5 AI 4K, 
          công nghệ 4K AI Upscaling, thước phim tái hiện rực rỡ, sinh động nhờ HDR10 Pro, âm thanh chân thực, phù hợp với từng thể loại với công nghệ AI Sound, 
          hệ điều hành webOS 22 với kho ứng dụng phong phú.', -- description - nvarchar(255)
          '50UQ7550PSF', -- product_id - varchar(255)
          N'Thông tin sản phẩm'  -- tile - nvarchar(255)
          ),
          ( N'Smart Tivi LG 4K 50 inch 50UQ8000PSC thanh mảnh, tinh tế, hiển thị khung hình 4K rực rỡ với độ tương phản HDR10 Pro, 
          âm thanh tinh chỉnh AI Sound và AI Acoustic Tuning sống động, mang đến cho bạn trải nghiệm nghe nhìn đầy ấn tượng, hài lòng.', -- description - nvarchar(255)
          '50UQ8000PSC', -- product_id - varchar(255)
          N'Thông tin sản phẩm'  -- tile - nvarchar(255)
          ),
          ( N'Smart Tivi LG 4K 55 inch 55UQ7550PSF cung cấp màn hình trải nghiệm tuyệt đẹp với hình ảnh độ nét 4K, khung hình sống động nhờ bộ xử lý α5 Gen5 AI 4K, 
          cảm nhận trọn vẹn tính nghệ thuật của bộ phim với chế độ FilmMaker Mode, âm thanh AI Sound chân thực, hỗ trợ LG Voice Search tìm kiếm bằng giọng nói tiếng Việt dễ dàng.', -- description - nvarchar(255)
          '55UQ7550PSF', -- product_id - varchar(255)
          N'Thông tin sản phẩm'  -- tile - nvarchar(255)
          );
GO

INSERT INTO dbo.image
        ( image, product_id )
VALUES  ( 'QA55Q65A_1.png', -- image - varchar(255)
          'QA55Q65A'  -- product_id - varchar(255)
        ),
		( 'QA55Q65A_2.png', -- image - varchar(255)
          'QA55Q65A'  -- product_id - varchar(255)
        ),
		( 'QA55Q65A_3.png', -- image - varchar(255)
          'QA55Q65A'  -- product_id - varchar(255)
        ),
		( '55NANO76SQA_1.png', -- image - varchar(255)
          '55NANO76SQA'  -- product_id - varchar(255)
        ),
		( '55NANO76SQA_2.png', -- image - varchar(255)
          '55NANO76SQA'  -- product_id - varchar(255)
        ),
		( '55NANO76SQA_3.png', -- image - varchar(255)
          '55NANO76SQA'  -- product_id - varchar(255)
        ),
		( '32V35KP_1.png', -- image - varchar(255)
          '32V35KP'  -- product_id - varchar(255)
        ),
		( '32V35KP_2.png', -- image - varchar(255)
          '32V35KP'  -- product_id - varchar(255)
        ),
		( '32V35KP_3.png', -- image - varchar(255)
          '32V35KP'  -- product_id - varchar(255)
        ),
		( 'RT25M4032BU_1.png', -- image - varchar(255)
          'RT25M4032BU'  -- product_id - varchar(255)
        ),
		( 'RT25M4032BU_2.png', -- image - varchar(255)
          'RT25M4032BU'  -- product_id - varchar(255)
        ),
		( 'RT25M4032BU_3.png', -- image - varchar(255)
          'RT25M4032BU'  -- product_id - varchar(255)
        ),
		( 'GR-B256JDS_1.png', -- image - varchar(255)
          'GR-B256JDS'  -- product_id - varchar(255)
        ),
		( 'GR-B256JDS_2.png', -- image - varchar(255)
          'GR-B256JDS'  -- product_id - varchar(255)
        ),
		( 'GR-B256JDS_3.png', -- image - varchar(255)
          'GR-B256JDS'  -- product_id - varchar(255)
        ),
		( 'GR-RF610WE-PGV(22)-XK_1.png', -- image - varchar(255)
          'GR-RF610WE-PGV(22)-XK'  -- product_id - varchar(255)
        ),
		( 'GR-RF610WE-PGV(22)-XK_2.png', -- image - varchar(255)
          'GR-RF610WE-PGV(22)-XK'  -- product_id - varchar(255)
        ),
		( 'GR-RF610WE-PGV(22)-XK_3.png', -- image - varchar(255)
          'GR-RF610WE-PGV(22)-XK'  -- product_id - varchar(255)
        ),
		( 'WW80T3020WW_1.png', -- image - varchar(255)
          'WW80T3020WW'  -- product_id - varchar(255)
        ),
		( 'WW80T3020WW_2.png', -- image - varchar(255)
          'WW80T3020WW'  -- product_id - varchar(255)
        ),
		( 'WW80T3020WW_3.png', -- image - varchar(255)
          'WW80T3020WW'  -- product_id - varchar(255)
        ),
		( 'T2351VSAB_1.png', -- image - varchar(255)
          'T2351VSAB'  -- product_id - varchar(255)
        ),
		( 'T2351VSAB_2.png', -- image - varchar(255)
          'T2351VSAB'  -- product_id - varchar(255)
        ),
		( 'T2351VSAB_3.png', -- image - varchar(255)
          'T2351VSAB'  -- product_id - varchar(255)
        ),
		( 'AW-L805AV_1.png', -- image - varchar(255)
          'AW-L805AV'  -- product_id - varchar(255)
        ),
		( 'AW-L805AV_2.png', -- image - varchar(255)
          'AW-L805AV'  -- product_id - varchar(255)
        ),
		( 'AW-L805AV_3.png', -- image - varchar(255)
          'AW-L805AV'  -- product_id - varchar(255)
        ),
		( 'AR13CYFAAWKNSV_1.png', -- image - varchar(255)
          'AR13CYFAAWKNSV'  -- product_id - varchar(255)
        ),
		( 'AR13CYFAAWKNSV_2.png', -- image - varchar(255)
          'AR13CYFAAWKNSV'  -- product_id - varchar(255)
        ),
		( 'AR13CYFAAWKNSV_3.png', -- image - varchar(255)
          'AR13CYFAAWKNSV'  -- product_id - varchar(255)
        ),
		( 'ZTNQ18GPLA0_1.png', -- image - varchar(255)
          'ZTNQ18GPLA0'  -- product_id - varchar(255)
        ),
		( 'ZTNQ18GPLA0_2.png', -- image - varchar(255)
          'ZTNQ18GPLA0'  -- product_id - varchar(255)
        ),
		( 'ZTNQ18GPLA0_3.png', -- image - varchar(255)
          'ZTNQ18GPLA0'  -- product_id - varchar(255)
        ),
		( 'RAS-H10Z1KCVG-V_1.png', -- image - varchar(255)
          'RAS-H10Z1KCVG-V'  -- product_id - varchar(255)
        ),
		( 'ZTNQ18GPLA0_2.png', -- image - varchar(255)
          'RAS-H10Z1KCVG-V'  -- product_id - varchar(255)
        ),
		( 'RAS-H10Z1KCVG-V_3.png', -- image - varchar(255)
          'RAS-H10Z1KCVG-V'  -- product_id - varchar(255)
        ),
		( 'MSAGA-10CRDN8_1.png', -- image - varchar(255)
          'MSAGA-10CRDN8'  -- product_id - varchar(255)
        ),
		( 'MSAGA-10CRDN8_2.png', -- image - varchar(255)
          'MSAGA-10CRDN8'  -- product_id - varchar(255)
        ),
		( 'MSAGA-10CRDN8_3.png', -- image - varchar(255)
          'MSAGA-10CRDN8'  -- product_id - varchar(255)
        ),
    ( 'QA43Q65A_1.png', -- image - varchar(255)
          'QA43Q65A'  -- product_id - varchar(255)
        ),
		( 'QA43Q65A_2.png', -- image - varchar(255)
          'QA43Q65A'  -- product_id - varchar(255)
        ),
		( 'QA43Q65A_3.png', -- image - varchar(255)
          'QA43Q65A'  -- product_id - varchar(255)
        ),
        ( 'UA43BU8000_1.png', -- image - varchar(255)
          'UA43BU8000'  -- product_id - varchar(255)
        ),
		( 'UA43BU8000_2.png', -- image - varchar(255)
          'UA43BU8000'  -- product_id - varchar(255)
        ),
		( 'UA43BU8000_3.png', -- image - varchar(255)
          'UA43BU8000'  -- product_id - varchar(255)
        ),
        ( 'UA55AU8100_1.png', -- image - varchar(255)
          'UA55AU8100'  -- product_id - varchar(255)
        ),
		( 'UA55AU8100_2.png', -- image - varchar(255)
          'UA55AU8100'  -- product_id - varchar(255)
        ),
		( 'UA55AU8100_3.png', -- image - varchar(255)
          'UA55AU8100'  -- product_id - varchar(255)
        ),
        ( 'UA43AU7002_1.png', -- image - varchar(255)
          'UA43AU7002'  -- product_id - varchar(255)
        ),
		( 'UA43AU7002_2.png', -- image - varchar(255)
          'UA43AU7002'  -- product_id - varchar(255)
        ),
		( 'UA43AU7002_3.png', -- image - varchar(255)
          'UA43AU7002'  -- product_id - varchar(255)
        ),
        ( 'UA65AU8100_1.png', -- image - varchar(255)
          'UA65AU8100'  -- product_id - varchar(255)
        ),
		( 'UA65AU8100_2.png', -- image - varchar(255)
          'UA65AU8100'  -- product_id - varchar(255)
        ),
		( 'UA65AU8100_3.png', -- image - varchar(255)
          'UA65AU8100'  -- product_id - varchar(255)
        ),
        ( 'QA65Q65A_1.png', -- image - varchar(255)
          'QA65Q65A'  -- product_id - varchar(255)
        ),
		( 'QA65Q65A_2.png', -- image - varchar(255)
          'QA65Q65A'  -- product_id - varchar(255)
        ),
		( 'QA65Q65A_3.png', -- image - varchar(255)
          'QA65Q65A'  -- product_id - varchar(255)
        ),
        ( 'QA50Q65A_1.png', -- image - varchar(255)
          'QA50Q65A'  -- product_id - varchar(255)
        ),
		( 'QA50Q65A_2.png', -- image - varchar(255)
          'QA50Q65A'  -- product_id - varchar(255)
        ),
		( 'QA50Q65A_3.png', -- image - varchar(255)
          'QA50Q65A'  -- product_id - varchar(255)
        ),
        ( 'UA55BU8000_1.png', -- image - varchar(255)
          'UA55BU8000'  -- product_id - varchar(255)
        ),
		( 'UA55BU8000_2.png', -- image - varchar(255)
          'UA55BU8000'  -- product_id - varchar(255)
        ),
		( 'UA55BU8000_3.png', -- image - varchar(255)
          'UA55BU8000'  -- product_id - varchar(255)
        ),
        ( 'QA65Q60B_1.png', -- image - varchar(255)
          'QA65Q60B'  -- product_id - varchar(255)
        ),
		( 'QA65Q60B_2.png', -- image - varchar(255)
          'QA65Q60B'  -- product_id - varchar(255)
        ),
		( 'QA65Q60B_3.png', -- image - varchar(255)
          'QA65Q60B'  -- product_id - varchar(255)
        ),
        ( '55UQ8000PSC_1.png', -- image - varchar(255)
          '55UQ8000PSC'  -- product_id - varchar(255)
        ),
		( '55UQ8000PSC_2.png', -- image - varchar(255)
          '55UQ8000PSC'  -- product_id - varchar(255)
        ),
		( '55UQ8000PSC_3.png', -- image - varchar(255)
          '55UQ8000PSC'  -- product_id - varchar(255)
        ),
        ( '65NANO76SQA_1.png', -- image - varchar(255)
          '65NANO76SQA'  -- product_id - varchar(255)
        ),
		( '65NANO76SQA_2.png', -- image - varchar(255)
          '65NANO76SQA'  -- product_id - varchar(255)
        ),
		( '65NANO76SQA_3.png', -- image - varchar(255)
          '65NANO76SQA'  -- product_id - varchar(255)
        ),
        ( '65UQ8000PSC_1.png', -- image - varchar(255)
          '65UQ8000PSC'  -- product_id - varchar(255)
        ),
		( '65UQ8000PSC_2.png', -- image - varchar(255)
          '65UQ8000PSC'  -- product_id - varchar(255)
        ),
		( '65UQ8000PSC_3.png', -- image - varchar(255)
          '65UQ8000PSC'  -- product_id - varchar(255)
        ),
        ( '43UQ7550PSF_1.png', -- image - varchar(255)
          '43UQ7550PSF'  -- product_id - varchar(255)
        ),
		( '43UQ7550PSF_2.png', -- image - varchar(255)
          '43UQ7550PSF'  -- product_id - varchar(255)
        ),
		( '43UQ7550PSF_3.png', -- image - varchar(255)
          '43UQ7550PSF'  -- product_id - varchar(255)
        ),
        ( '43UQ8000PSC_1.png', -- image - varchar(255)
          '43UQ8000PSC'  -- product_id - varchar(255)
        ),
		( '43UQ8000PSC_2.png', -- image - varchar(255)
          '43UQ8000PSC'  -- product_id - varchar(255)
        ),
		( '43UQ8000PSC_3.png', -- image - varchar(255)
          '43UQ8000PSC'  -- product_id - varchar(255)
        ),
        ( '43NANO76SQA_1.png', -- image - varchar(255)
          '43NANO76SQA'  -- product_id - varchar(255)
        ),
		( '43NANO76SQA_2.png', -- image - varchar(255)
          '43NANO76SQA'  -- product_id - varchar(255)
        ),
		( '43NANO76SQA_3.png', -- image - varchar(255)
          '43NANO76SQA'  -- product_id - varchar(255)
        ),
        ( '50UQ7550PSF_1.png', -- image - varchar(255)
          '50UQ7550PSF'  -- product_id - varchar(255)
        ),
		( '50UQ7550PSF_2.png', -- image - varchar(255)
          '50UQ7550PSF'  -- product_id - varchar(255)
        ),
		( '50UQ7550PSF_3.png', -- image - varchar(255)
          '50UQ7550PSF'  -- product_id - varchar(255)
        ),
        ( '50UQ8000PSC_1.png', -- image - varchar(255)
          '50UQ8000PSC'  -- product_id - varchar(255)
        ),
		( '50UQ8000PSC_2.png', -- image - varchar(255)
          '50UQ8000PSC'  -- product_id - varchar(255)
        ),
		( '50UQ8000PSC_3.png', -- image - varchar(255)
          '50UQ8000PSC'  -- product_id - varchar(255)
        ),
        ( '55UQ7550PSF_1.png', -- image - varchar(255)
          '55UQ7550PSF'  -- product_id - varchar(255)
        ),
		( '55UQ7550PSF_2.png', -- image - varchar(255)
          '55UQ7550PSF'  -- product_id - varchar(255)
        ),
		( '55UQ7550PSF_3.png', -- image - varchar(255)
          '55UQ7550PSF'  -- product_id - varchar(255)
        );
GO 

INSERT INTO dbo.product_detail
        ( power ,
          quantity_in_stock ,
          weight ,
          production_date ,
          color ,
          product_id ,
          size,
		  status
        )
VALUES  ( 50 ,10 ,8 ,SYSDATETIME() ,'White' ,'QA55Q65A' ,'130cmx80cmx6cm', 0 ),
        ( 50 ,10 ,8 ,SYSDATETIME() ,'Black' ,'QA55Q65A' ,'130cmx80cmx6cm' , 0 ),
        ( 50 ,10 ,8 ,SYSDATETIME() ,'White' ,'QA55Q65A' ,'130cmx60cmx6cm', 0 ),
        ( 50 ,10 ,8 ,SYSDATETIME() ,'Black' ,'QA55Q65A' ,'130cmx60cmx6cm', 0 ),
        ( 50 ,10 ,8 ,SYSDATETIME() ,'Black' ,'55NANO76SQA' ,'120cmx70cmx6cm', 0 ),
        ( 50 ,10 ,8 ,SYSDATETIME() ,'Black' ,'32V35KP' ,'150cmx65cmx8cm', 0 ),
        ( 50 ,10 ,8 ,SYSDATETIME() ,'Black' ,'QA43Q65A' ,'120cmx80cmx8cm' , 0),
        ( 50 ,10 ,8 ,SYSDATETIME() ,'Black' ,'UA43BU8000' ,'130cmx80cmx8cm', 0 ),
        ( 50 ,10 ,8 ,SYSDATETIME() ,'Black' ,'UA55AU8100' ,'140cmx90cmx6cm' , 0),
        ( 50 ,10 ,8 ,SYSDATETIME() ,'Black' ,'UA43AU7002' ,'130cmx100cmx6cm', 0 ),
        ( 50 ,10 ,8 ,SYSDATETIME() ,'Black' ,'UA65AU8100' ,'130cmx85cmx6cm', 0 ),
        ( 50 ,10 ,8 ,SYSDATETIME() ,'Black' ,'QA65Q65A' ,'130cmx83cmx8cm', 0 ),
        ( 50 ,10 ,8 ,SYSDATETIME() ,'Black' ,'QA50Q65A' ,'130cmx80cmx7cm', 0 ),
        ( 50 ,8 ,8 ,SYSDATETIME() ,'Black' ,'UA55BU8000' ,'130cmx80cmx9cm' , 0),
        ( 50 ,10 ,8 ,SYSDATETIME() ,'Black' ,'QA65Q60B' ,'140cmx80cmx6cm', 0 ),
        ( 50 ,6 ,8 ,SYSDATETIME() ,'Black' ,'55UQ8000PSC' ,'133cmx80cmx6cm', 0 ),
        ( 50 ,10 ,8 ,SYSDATETIME() ,'Black' ,'65NANO76SQA' ,'135cmx60cmx6cm', 0 ),
        ( 50 ,10 ,8 ,SYSDATETIME() ,'Black' ,'65UQ8000PSC' ,'120cmx70cmx6cm', 0 ),
        ( 50 ,1 ,8 ,SYSDATETIME() ,'Black' ,'43UQ7550PSF' ,'135cmx80cmx6cm', 0 ),
        ( 50 ,10 ,8 ,SYSDATETIME() ,'Black' ,'43UQ8000PSC' ,'138cmx80cmx6cm', 0 ),
        ( 50 ,3 ,8 ,SYSDATETIME() ,'Black' ,'43NANO76SQA' ,'141cmx80cmx8cm', 0 ),
        ( 50 ,10 ,8 ,SYSDATETIME() ,'Black' ,'50UQ7550PSF' ,'150cmx80cmx6cm', 0 ),
        ( 50 ,10 ,8 ,SYSDATETIME() ,'Black' ,'50UQ8000PSC' ,'135cmx70cmx8cm', 0 ),
        ( 50 ,10 ,8 ,SYSDATETIME() ,'Black' ,'55UQ7550PSF' ,'130cmx60cmx6cm' , 0),
        ( 50 ,10 ,8 ,SYSDATETIME() ,'Black' ,'RT25M4032BU' ,'160cmx80cmx6cm' , 0),
        ( 50 ,10 ,8 ,SYSDATETIME() ,'Black' ,'GR-B256JDS' ,'140cmx80cmx6cm' , 0),
        ( 50 ,10 ,8 ,SYSDATETIME() ,'Black' ,'GR-RF610WE-PGV(22)-XK' ,'130cmx90cmx6cm', 0 ),
        ( 50 ,10 ,8 ,SYSDATETIME() ,'Black' ,'WW80T3020WW' ,'130cmx85cmx6cm', 0 ),
        ( 50 ,10 ,8 ,SYSDATETIME() ,'Black' ,'T2351VSAB' ,'130cmx85cmx8cm', 0 ),
        ( 50 ,10 ,8 ,SYSDATETIME() ,'Black' ,'AW-L805AV' ,'130cmx88cmx10cm' , 0),
        ( 50 ,10 ,8 ,SYSDATETIME() ,'Black' ,'AR13CYFAAWKNSV' ,'180cmx80cmx11cm', 0 ),
        ( 50 ,10 ,8 ,SYSDATETIME() ,'Black' ,'ZTNQ18GPLA0' ,'170cmx80cmx8cm', 0 ),
        ( 50 ,10 ,8 ,SYSDATETIME() ,'Black' ,'RAS-H10Z1KCVG-V' ,'140cmx80cmx8cm', 0 ),
        ( 50 ,10 ,8 ,SYSDATETIME() ,'Black' ,'MSAGA-10CRDN8' ,'130cmx80cmx9cm', 0 );
GO        

INSERT INTO dbo.price
        ( price ,
          created_date ,
          product_detail_id
        )
VALUES  ( 23000000,SYSDATETIME(), 1 ),
        ( 16000000 ,SYSDATETIME(), 2 ),
        ( 9000000 ,SYSDATETIME(), 3 ),
        ( 9990000 ,SYSDATETIME(), 4 ),
        ( 8250000 ,SYSDATETIME(), 5 ),
        ( 11090000 ,SYSDATETIME(), 6 ),
        ( 6890000 ,SYSDATETIME(), 7 ),
        ( 12890000 ,SYSDATETIME(), 8 ),
        ( 15490000 ,SYSDATETIME(), 9 ),
        ( 10990000 ,SYSDATETIME(), 10 ),
        ( 10590000 ,SYSDATETIME(), 11 ),
        ( 14990000 ,SYSDATETIME(), 12 ),
        ( 10990000 ,SYSDATETIME(), 13 ),
        ( 14990000 ,SYSDATETIME(), 14 ),
        ( 12990000 ,SYSDATETIME(), 15 ),
        ( 7990000 ,SYSDATETIME(), 16 ),
        ( 8390000 ,SYSDATETIME(), 17 ),
        ( 9990000 ,SYSDATETIME(), 18 ),
        ( 9490000 ,SYSDATETIME(), 19 ),
        ( 9990000 ,SYSDATETIME(), 20 ),
        ( 10490000 ,SYSDATETIME(), 21 ),
        ( 19000000 ,SYSDATETIME(), 22 ),
        ( 22000000 ,SYSDATETIME(), 23 ),
        ( 11500000 ,SYSDATETIME(), 24 ),
        ( 11500000 ,SYSDATETIME(), 25 ),
        ( 15000000 ,SYSDATETIME(), 26 ),
        ( 9500000 ,SYSDATETIME(), 27 ),
        ( 14500000 ,SYSDATETIME(), 28 ),
        ( 18500000 ,SYSDATETIME(), 29 ),
        ( 8500000 ,SYSDATETIME(), 30 ),
        ( 12000000 ,SYSDATETIME(), 31),
        ( 18500000 ,SYSDATETIME(), 32 ),
        ( 8500000 ,SYSDATETIME(), 33 ),
        ( 12000000 ,SYSDATETIME(), 34 );
GO 

INSERT INTO dbo.orders
        ( total_amount ,
          order_date ,
          delivery_address ,
          user_id,
		  payment_id,
		  delivery_fee ,
		  expected_delivery_time ,
		  discount,
          note ,
		  nickname 
        )
VALUES  ( 18000000 , -- total_amount - float
          '2023-09-01 13:10:50' , -- invoice_date - datetime2(6)
          N'ĐC: số 123 đường số 4,Xã Thới Hưng, Huyện Cờ Đỏ, Thành Phố Cần Thơ' , -- note - nvarchar(255)
          'linhptpc04737',  -- user_id - varchar(255)
		  1,
		  15000,
		  '2023-09-06 10:00:00',
		  10000,
          N'Không' ,
		  N'Baba' 
        ),
		( 17980000 , -- total_amount - float
          '2023-10-01 13:30:50' , -- invoice_date - datetime2(6)
          N'ĐC: số 123 đường số 4,Xã Thới Hưng, Huyện Cờ Đỏ, Thành Phố Cần Thơ' , -- note - nvarchar(255)
          'linhptpc04737',  -- user_id - varchar(255)
          1,
		  15000,
		  '2023-10-07 10:30:00',
		  29000,
          N'Không' ,
		  N'Baba'
        ),
		( 44650000 , -- total_amount - float
          '2023-11-05 13:15:50' , -- invoice_date - datetime2(6)
          N'ĐC: số 123 đường số 4,Xã Thới Hưng, Huyện Cờ Đỏ, Thành Phố Cần Thơ' , -- note - nvarchar(255)
          'linhptpc04737',  -- user_id - varchar(255)
          1,
		  15000,
		  '2023-11-10 11:00:00',
		  10000,
          N'Không' ,
		  N'Baba'
        ),
		( 144950000 , -- total_amount - float
          '2023-11-10 13:10:50' , -- invoice_date - datetime2(6)
          N'ĐC: số 123 đường số 4,Xã Thới Hưng, Huyện Cờ Đỏ, Thành Phố Cần Thơ' , -- note - nvarchar(255)
          'linhptpc04737',  -- user_id - varchar(255)
		  1,
		  15000,
		  '2023-11-15 10:00:00',
		  10000,
          N'Không' ,
		  N'Baba'
        ),
		( 374800000 , -- total_amount - float
          '2023-11-22 15:10:50' , -- invoice_date - datetime2(6)
          N'ĐC: số 123 đường số 4,Xã Thới Hưng, Huyện Cờ Đỏ, Thành Phố Cần Thơ' , -- note - nvarchar(255)
          'linhptpc04737',  -- user_id - varchar(255)
          1,
		  15000,
		  '2023-11-16 11:00:00',
		  10000,
          N'Không' ,
		  N'Baba'
        ),
		( 11090000 , -- total_amount - float
          '2023-12-01 19:10:50' , -- invoice_date - datetime2(6)
          N'ĐC: số 123 đường số 4,Xã Thới Hưng, Huyện Cờ Đỏ, Thành Phố Cần Thơ' , -- note - nvarchar(255)
          'linhptpc04737',  -- user_id - varchar(255)
          1,
		  15000,
		  '2023-12-05 10:00:00',
		  10000,
          N'Không' ,
		  N'Baba'
        ),
		( 10590000 , -- total_amount - float
          '2023-12-07 21:10:50' , -- invoice_date - datetime2(6)
          N'ĐC: số 123 đường số 4,Xã Thới Hưng, Huyện Cờ Đỏ, Thành Phố Cần Thơ' , -- note - nvarchar(255)
          'linhptpc04737',  -- user_id - varchar(255)
          2,
		  15000,
		  '2023-12-10 15:00:00',
		  39000,
          N'Không' ,
		  N'Baba'
        );
GO

INSERT INTO dbo.delivery_status
        ( name_status )
VALUES  ( N'Đang xử lý'  -- name_status - nvarchar(255)
          ),
		  ( N'Đang giao hàng'  -- name_status - nvarchar(255)
          ),
		  ( N'Giao hàng thành công'  -- name_status - nvarchar(255)
          ),
		  ( N'Đã hủy'  -- name_status - nvarchar(255)
          );
GO 

INSERT INTO dbo.order_delievery_status
        ( delivery_id, order_id, status, date )
VALUES  ( 1, -- delivery_id - int
          1, -- order_id - int
          0, -- status - bit
          '2023-12-02 00:00:00'  -- date - datetime2(6)
          ),
		  ( 2, -- delivery_id - int
          1, -- order_id - int
          1, -- status - bit
          SYSDATETIME()  -- date - datetime2(6)
          ),
		  ( 1, -- delivery_id - int
          2, -- order_id - int
          1, -- status - bit
          SYSDATETIME()  -- date - datetime2(6)
          ),
		  ( 1, -- delivery_id - int
          3, -- order_id - int
          1, -- status - bit
          SYSDATETIME()  -- date - datetime2(6)
          ),
		  ( 1, -- delivery_id - int
          4, -- order_id - int
          1, -- status - bit
          SYSDATETIME()  -- date - datetime2(6)
          ),
		  ( 1, -- delivery_id - int
          5, -- order_id - int
          1, -- status - bit
          SYSDATETIME()  -- date - datetime2(6)
          ),
		  ( 1, -- delivery_id - int
          6, -- order_id - int
          1, -- status - bit
          SYSDATETIME()  -- date - datetime2(6)
          ),
		  ( 1, -- delivery_id - int
          7, -- order_id - int
          1, -- status - bit
          SYSDATETIME()  -- date - datetime2(6)
          )


INSERT INTO dbo.order_detail
        ( order_id ,
          price ,
          quantity ,
          product_detail_id
        )
VALUES  ( 1 , -- invoice_id - int
          9000000 , -- price - numeric(38, 2)
          2 , -- quantity - int
          1
        ),
        ( 1 , -- invoice_id - int
          9000000 , -- price - numeric(38, 2)
          2 , -- quantity - int
          2
        ),
        ( 1 , -- invoice_id - int
          9900000 , -- price - numeric(38, 2)
          2 , -- quantity - int
          3
        ),
        ( 1 , -- invoice_id - int
          9900000 , -- price - numeric(38, 2)
          2 , -- quantity - int
          4
        ),
		( 2 , -- invoice_id - int
          9990000 , -- price - numeric(38, 2)
          1 , -- quantity - int
          5
        ),
		( 2 , -- invoice_id - int
          7990000 , -- price - numeric(38, 2)
          1 , -- quantity - int
          6
        ),
		( 3 , -- invoice_id - int
          8390000 , -- price - numeric(38, 2)
          3 , -- quantity - int
          7
        ),
		( 3 , -- invoice_id - int
          9490000 , -- price - numeric(38, 2)
          1 , -- quantity - int
          8
        ),
		( 3 , -- invoice_id - int
          9990000 , -- price - numeric(38, 2)
          1 , -- quantity - int
          9
        ),
		( 4 , -- invoice_id - int
          16000000 , -- price - numeric(38, 2)
          1 , -- quantity - int
          10
        ),
		( 4 , -- invoice_id - int
          10490000 , -- price - numeric(38, 2)
          1 , -- quantity - int
          11
        ),
		( 4 , -- invoice_id - int
          10990000 , -- price - numeric(38, 2)
          1 , -- quantity - int
          12
        ),
		( 4 , -- invoice_id - int
          14990000 , -- price - numeric(38, 2)
          1 , -- quantity - int
          13
        ),
		( 4 , -- invoice_id - int
          12990000 , -- price - numeric(38, 2)
          1 , -- quantity - int
          14
        ),
		( 4 , -- invoice_id - int
          14500000 , -- price - numeric(38, 2)
          1 , -- quantity - int
          15
        ),
		( 4 , -- invoice_id - int
          9500000 , -- price - numeric(38, 2)
          1 , -- quantity - int
          16
        ),
		( 4 , -- invoice_id - int
          22000000 , -- price - numeric(38, 2)
          1 , -- quantity - int
          17
        ),
		( 4 , -- invoice_id - int
          11500000 , -- price - numeric(38, 2)
          1 , -- quantity - int
          18
        ),
		( 4 , -- invoice_id - int
          12000000 , -- price - numeric(38, 2)
          1 , -- quantity - int
          19
        ),
		( 4 , -- invoice_id - int
          9990000 , -- price - numeric(38, 2)
          1 , -- quantity - int
          20
        ),
		( 5 , -- invoice_id - int
          10990000 , -- price - numeric(38, 2)
          10 , -- quantity - int
          21
        ),
		( 5 , -- invoice_id - int
          23000000 , -- price - numeric(38, 2)
          5 , -- quantity - int
          22
        ),
		( 5 , -- invoice_id - int
          14990000 , -- price - numeric(38, 2)
          10 , -- quantity - int
          23
        ),
		( 6 , -- invoice_id - int
          11090000 , -- price - numeric(38, 2)
          1 , -- quantity - int
          24
        ),
		( 7 , -- invoice_id - int
          10590000 , -- price - numeric(38, 2)
          1 , -- quantity - int
          25
        );

GO 

INSERT INTO dbo.comment
        ( date ,
          star ,
          content ,
          product_detail_id ,
          user_comment,
		  order_detail_id
        )
VALUES  ( '2023-10-02 20:05:00' , -- date - date
          5 , -- star - int
          N'Giao hàng nhanh, đóng gói cẩn thận !' , -- content - nvarchar(255)
          1 , -- product_id - varchar(255)
          'linhptpc04737',  -- user_comment - varchar(255)
		  1
        ),
		( '2023-10-06 20:10:00' , -- date - date
          4 , -- star - int
          N'Giao hàng nhanh, nhân viên thân thiện !' , -- content - nvarchar(255)
          2 , -- product_id - varchar(255)
          'linhptpc04737',  -- user_comment - varchar(255)
		  2
        ),
		( '2023-10-06 20:15:00' , -- date - date
          4 , -- star - int
          N'Giao hàng nhanh, nhân viên thân thiện !' , -- content - nvarchar(255)
          3 , -- product_id - varchar(255)
          'linhptpc04737',  -- user_comment - varchar(255)
		  3
        ),
		( '2023-10-06 20:20:00' , -- date - date
          5 , -- star - int
          N'Giao hàng nhanh, nhân viên thân thiện, nhiệt tình !' , -- content - nvarchar(255)
          4 , -- product_id - varchar(255)
          'linhptpc04737',  -- user_comment - varchar(255)
          4
        ),
		( '2023-10-06 20:25:00' , -- date - date
          5 , -- star - int
          N'Giao hàng nhanh, nhân viên thân thiện, nhiệt tình !' , -- content - nvarchar(255)
          5 , -- product_id - varchar(255)
          'linhptpc04737',  -- user_comment - varchar(255)
          5
        ),
		( '2023-10-06 20:30:00' , -- date - date
          5 , -- star - int
          N'Giao hàng nhanh, nhân viên thân thiện, nhiệt tình !' , -- content - nvarchar(255)
          6 , -- product_id - varchar(255)
          'linhptpc04737',  -- user_comment - varchar(255)
          6
        ),
		( '2023-10-11 20:35:00' , -- date - date
          5 , -- star - int
          N'Giao hàng nhanh, đóng gói cẩn thận !' , -- content - nvarchar(255)
          7 , -- product_id - varchar(255)
          'linhptpc04737',  -- user_comment - varchar(255)
          7
        ),
		( '2023-10-11 20:40:00' , -- date - date
          5 , -- star - int
          N'Giao hàng nhanh, đóng gói cẩn thận !' , -- content - nvarchar(255)
          8 , -- product_id - varchar(255)
          'linhptpc04737',  -- user_comment - varchar(255)
          8
        ),
		( '2023-10-11 20:45:00' , -- date - date
          5 , -- star - int
          N'Giao hàng nhanh, đóng gói cẩn thận !' , -- content - nvarchar(255)
          9 , -- product_id - varchar(255)
          'linhptpc04737',  -- user_comment - varchar(255)
          9
        ),
		( '2023-10-11 20:50:00' , -- date - date
          5 , -- star - int
          N'Giao hàng nhanh, đóng gói cẩn thận !' , -- content - nvarchar(255)
          10 , -- product_id - varchar(255)
          'linhptpc04737',  -- user_comment - varchar(255)
          10
        ),
		( '2023-10-11 20:55:00' , -- date - date
          5 , -- star - int
          N'Giao hàng nhanh, đóng gói cẩn thận !' , -- content - nvarchar(255)
          11 , -- product_id - varchar(255)
          'linhptpc04737',  -- user_comment - varchar(255)
          11
        ),
		( '2023-10-11 21:00:00' , -- date - date
          5 , -- star - int
          N'Giao hàng nhanh, đóng gói cẩn thận !' , -- content - nvarchar(255)
         12 , -- product_id - varchar(255)
          'linhptpc04737',  -- user_comment - varchar(255)
          12
        ),
		( '2023-10-11 21:05:00' , -- date - date
          5 , -- star - int
          N'Giao hàng nhanh, đóng gói cẩn thận !' , -- content - nvarchar(255)
          13 , -- product_id - varchar(255)
          'linhptpc04737',  -- user_comment - varchar(255)
          13
        ),
		( '2023-10-11 21:10:00' , -- date - date
          5 , -- star - int
          N'Giao hàng nhanh, đóng gói cẩn thận !' , -- content - nvarchar(255)
          14 , -- product_id - varchar(255)
          'linhptpc04737',  -- user_comment - varchar(255)
          14
        ),
		( '2023-10-11 21:15:00' , -- date - date
          5 , -- star - int
          N'Giao hàng nhanh, đóng gói cẩn thận !' , -- content - nvarchar(255)
          15 , -- product_id - varchar(255)
          'linhptpc04737',  -- user_comment - varchar(255)
          15
        ),
		( '2023-10-11 21:20:00' , -- date - date
          5 , -- star - int
          N'Giao hàng nhanh, đóng gói cẩn thận !' , -- content - nvarchar(255)
         16, -- product_id - varchar(255)
          'linhptpc04737',  -- user_comment - varchar(255)
          16
        ),
		( '2023-10-11 21:25:00' , -- date - date
          3 , -- star - int
          N'Giao hàng nhanh, tuy nhiên sản phẩm bị cấn móp !' , -- content - nvarchar(255)
         17 , -- product_id - varchar(255)
          'linhptpc04737',  -- user_comment - varchar(255)
          17
        ),
		( '2023-10-16 21:30:00' , -- date - date
          5 , -- star - int
          N'Giao hàng nhanh, đóng gói cẩn thận !' , -- content - nvarchar(255)
          18 , -- product_id - varchar(255)
          'linhptpc04737',  -- user_comment - varchar(255)
          18
        ),
		( '2023-10-16 21:35:00' , -- date - date
          5 , -- star - int
          N'Giao hàng nhanh, đóng gói cẩn thận !' , -- content - nvarchar(255)
          19 , -- product_id - varchar(255)
          'linhptpc04737',  -- user_comment - varchar(255)
          19
        ),
		( '2023-10-16 21:40:00' , -- date - date
          5 , -- star - int
          N'Giao hàng nhanh, đóng gói cẩn thận !' , -- content - nvarchar(255)
          20 , -- product_id - varchar(255)
          'linhptpc04737',  -- user_comment - varchar(255)
          20
        );
GO 

INSERT INTO dbo.image_comment
        ( comment_id, image )
VALUES  ( 10, -- comment_id - int
          '65NANO76SQA_1.png'  -- image - varchar(255)
          ),
		  ( 10, -- comment_id - int
          '65NANO76SQA_2.png'  -- image - varchar(255)
          ),
		  ( 10, -- comment_id - int
          '65NANO76SQA_3.png'  -- image - varchar(255)
          );
GO 
INSERT INTO dbo.promotional_details
        ( discounted_price ,
          flash_sale_id ,
          status ,
          product_id,
		  discounted_quantity,
		  quantity_sold
        )
VALUES  ( 0.15 , -- discounted_price - float
          3 , -- flash_sale_id - int
          0 , -- status - bit
          '32V35KP',  -- product_id - varchar(255)
          50,
		  20
        ),
		( 0.1 , -- discounted_price - float
          3 , -- flash_sale_id - int
          0 , -- status - bit
          '50UQ7550PSF',  -- product_id - varchar(255)
          50,
		  50
        ),
		( 0.5 , -- discounted_price - float
          3 , -- flash_sale_id - int
          0 , -- status - bit
          '65NANO76SQA',  -- product_id - varchar(255)
          50,
		  0
        ),
		( 0.1 , -- discounted_price - float
          3 , -- flash_sale_id - int
          0 , -- status - bit
          'AR13CYFAAWKNSV',  -- product_id - varchar(255)
          50,
		  0
        ),
		( 0.2 , -- discounted_price - float
          3 , -- flash_sale_id - int
          0 , -- status - bit
          'GR-B256JDS',  -- product_id - varchar(255)
          50,
		  0
        ),
		( 0.3 , -- discounted_price - float
          3 , -- flash_sale_id - int
          0 , -- status - bit
          'MSAGA-10CRDN8',  -- product_id - varchar(255)
          50,
		  0
        ),
		( 0.25 , -- discounted_price - float
          3 , -- flash_sale_id - int
          0 , -- status - bit
          'UA43BU8000',  -- product_id - varchar(255)
          50,
		  50
        ), 
		( 0.15 , -- discounted_price - float
          3 , -- flash_sale_id - int
          0 , -- status - bit
          'ZTNQ18GPLA0',  -- product_id - varchar(255)
          50,
		  40
        );

INSERT INTO dbo.cart
        ( product, quantity, user_id )
VALUES  ( 1, -- product - int
          1, -- quantity - int
          'linhptpc04737'  -- user_id - varchar(255)
          ),
		  ( 2, -- product - int
          1, -- quantity - int
          'linhptpc04737'  -- user_id - varchar(255)
          ),
		  ( 3, -- product - int
          1, -- quantity - int
          'linhptpc04737'  -- user_id - varchar(255)
          )

--SELECT * FROM dbo.order_detail
--SELECT * FROM dbo.user_custom
--SELECT invoice_id, SUM(c.price*c.quantity) FROM dbo.invoice_detail c INNER JOIN dbo.invoice ON invoice.id = c.invoice_id GROUP BY invoice_id