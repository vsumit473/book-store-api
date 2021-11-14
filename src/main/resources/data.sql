INSERT INTO category(id,category,discount,isenable) VALUES (1,'Comics','0',1),(2,'Fictional','15',0),(3,'Devotional','25',1),
(4,'Historical','5',1);

INSERT INTO book(book_id,name,description,author,type,price,isbn) VALUES(1,'Anna Karenina. Greta Garbo','dummy','Dummy Author',2,440,123),
(2,'The Great Gatsby','dummy','Dummy Author',2,550,456),
(3,'Don Quixote. Don Quixote','dummy','Dummy Author',1,1000,789),
(4,'One Hundred Years ','dummy','Dummy Author',1,240,1011),
(5,'Tarak Mehta','dummy','Dummy Author',4,580,1213),
(6,'Hatim','dummy','Dummy Author',3,350,1415);

INSERT INTO coupon(id,coupon_code,is_active,has_used,discount_amount) VALUES(1,'HAPPYDIWALI',1,0,100),(2,'WEEKEND',1,0,400),(3,'SUPER50',1,0,300),(4,'DUBAI',1,0,500),(5,'SUMITDUBAI',1,0,550),
(6,'ICICI',1,0,700),(7,'SBI',1,0,690),(8,'NIGHT',1,0,200),(9,'RBL',1,0,490)
