# Running Spring Batch application using AWS Batch 
How to trigger AWS Batch on S3 upload event & pass S3 BucketName and FileName(key) to the Spring Batch application

Following steps describe how to send S3 source Event MetaData (Bucket Name and File Name) as arguments to a Spring Batch application.

## 1. Cloudwatch Rule Changes- `Event Source` Section:
Remove `key` tag and *its value* completely from the Event Pattern of the batch Cloudwatch Rule. Add `key` tag in case you need to trigger event only when a file with specific name `key` is uploaded to the specified S3 bucket.
  
```
{
  "source": [
    "aws.s3"
  ],
  "detail-type": [
    "AWS API Call via CloudTrail"
  ],
  "detail": {
    "eventSource": [
      "s3.amazonaws.com"
    ],
    "eventName": [
      "PutObject",
      "CompleteMultipartUpload",
      "CopyObject"
    ],
    "requestParameters": {
      "bucketName": [
        "<WRITE_NAME_OF_YOUR_BUCKET>"
      ]
    }
  }
}
```

## 2. Cloudwatch Rule Changes- `Target` Section 
Choose Input Transformer option under Configure Input section:
 - Enter this in first text box: `{"S3KeyValue":"$.detail.requestParameters.key","S3BucketValue":"$.detail.requestParameters.bucketName"}`
 - Enter this in second text box: `{"Parameters" : {"S3bucket": <S3BucketValue>, "S3key": <S3KeyValue>}}`
 
## 3. Update AWS Batch `Job Definition`
Add the following to `Environment -> Command -> Space Delimited` text box: 
 - `Ref::S3bucket Ref::S3key`
 
## 4. Spring Batch Application
The values specified for `S3bucket` and `S3key` will be available to the Spring Batch application in `args[]` argument of `main()` method-

```
@SpringBootApplication
public class BatchProcessingApplication {

    public static void main(String[] args) throws Exception {
    	System.out.println("args:");
    	for (String arg : args) {
			System.out.println(arg); // This will print the S3bucket and S3key argument values
		}
        SpringApplication.run(BatchProcessingApplication.class, args);
    }
}
```