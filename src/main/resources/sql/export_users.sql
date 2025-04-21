SELECT ue.id          AS user_id,
       ue.username,
       ue.email,
       ue.first_name,
       ue.last_name,
       ue.enabled,
       ue.email_verified,
       ue.created_timestamp,
       c.type         AS credential_type,
       c.secret_data,
       c.credential_data,
       c.salt,
       c.created_date AS credential_created_date
FROM user_entity ue
         LEFT JOIN credential c ON ue.id = c.user_id AND c.type = 'password';
