RewriteEngine On
RewriteCond %{QUERY_STRING} ^id=123$
RewriteRule ^/?product\.php$ http://website.com.au/product_123.php? [L,R=301]


Or to make it more general:

RewriteEngine On
RewriteCond %{QUERY_STRING} ^id=([^&]+)
RewriteRule ^/?product\.php$ http://website.com.au/product_%1.php? [L,R=301]


RewriteCond %{QUERY_STRING} ^s=141&sid=5$
RewriteRule ^ https://www.example.com <https://www.example.com/> ? [R=301,L]


or if not working above

RewriteEngine on
RewriteCond %{QUERY_STRING} ^s=([\d+])&sid=([\d+])$
RewriteCond %1 ^141$
RewriteCond %2 ^5$
RewriteRule ^ http://example.com <http://example.com/> ? [R=301,L]
