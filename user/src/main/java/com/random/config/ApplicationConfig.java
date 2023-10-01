//package com.random;
//
//import com.amazonaws.auth.AWSStaticCredentialsProvider;
//import com.amazonaws.auth.BasicAWSCredentials;
//import com.amazonaws.services.secretsmanager.AWSSecretsManager;
//import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
//import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
//import com.amazonaws.services.secretsmanager.model.GetSecretValueResult;
//import com.google.gson.Gson;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import software.amazon.awssdk.regions.Region;
//
//import javax.sql.DataSource;
//
//@Configuration
//public class ApplicationConfig {
//
//    @Value("${cloud.aws.credentials.access-key}")
//    private String accessKey;
//    @Value("${cloud.aws.credentials.secret-key}")
//    private String secretkey;
//
//    private Gson gson = new Gson();
//
//    @Bean
//    public DataSource dataSource() {
//        AwsSecrets secrets = getSecret();
//        return DataSourceBuilder
//                .create()
//                .driverClassName("com.mysql.cj.jdbc.Driver")
//                .url("jdbc:" + "mysql" + "://" + "URL" + ":" + "3306" + "/hemdb")
//                .username(secrets.getUsername())
//                .password(secrets.getPassword())
//                .build();
//    }
//
//    public AwsSecrets getSecret() {
//
//        String secretName = "rds!db-686fc1cc-8fb4-4154-88db-2752b18dfed3";
//        Region region = Region.of("us-east-1");
//
//        AWSSecretsManager client = AWSSecretsManagerClientBuilder.standard()
//                .withRegion(String.valueOf(region))
//                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretkey)))
//                .build();
//
//        String secret;
//        GetSecretValueRequest getSecretValueRequest = new GetSecretValueRequest()
//                .withSecretId(secretName);
//        GetSecretValueResult getSecretValueResult = null;
//
//        try {
//            getSecretValueResult = client.getSecretValue(getSecretValueRequest);
//        } catch (Exception e) {
//            throw e;
//        }
//        if (getSecretValueResult.getSecretString() != null) {
//            secret = getSecretValueResult.getSecretString();
//            return gson.fromJson(secret, AwsSecrets.class);
//        }
//
//        return null;
//    }
//}
