### Terminal打包命令

    Windows OS: 
    		gradlew assembleXxxRelease----打包指定渠道发布包
    		gradlew assembleRelease-------打包所有渠道发布包
    		
            gradlew assembleGoogleRelease
            gradlew assembleRelease
            
			gradlew assembleDebug
			gradlew assembleGoogleDebug

	Mac OS:
			./gradlew assembleXxxRelease----打包指定渠道发布包

			./gradlew assembleGoogleRelease
            ./gradlew assembleSecondloanRelease
            ./gradlew assembleMobisummerRelease

            ./gradlew assembleGoogleDebug
            ./gradlew assembleGoogleDevtestDebug
            ./gradlew assembleDebug

			./gradlew assembleRelease-------打包所有渠道发布包
            ./gradlew assembleRelease
            ./gradlew assembleGoogleRelease
            ./gradlew assembleGoogleDevtestRelease
            
---
| 平台        | 打包命令        | 说明        |
| --------   | --------   | :-----: | 
| Windows        | gradlew assembleXxxRelease        | 打包指定渠道发布包, Xxx为渠道标识(首字符大写) |          
| Windows        | gradlew assembleGoogleRelease        | 打包google渠道 发布包      |  
| Windows        | gradlew assembleRelease        | 打包所有渠道 发布包      |  
|         |          |       |  
| Windows        | gradlew assembleDebug        | 打包所有渠道 测试包      |  
| Windows        | gradlew assembleGoogleDebug        | 打包google渠道 测试包      |  
|         |          |       |  
| Mac        | ./gradlew assembleXxxRelease        | 打包指定渠道发布包, Xxx为渠道标识(首字符大写) |          
| Mac        | ./gradlew assembleGoogleRelease        | 打包google渠道 发布包      |  
| Mac        | ./gradlew assembleRelease        | 打包所有渠道 发布包      |  
|         |          |       |  
| Mac        | ./gradlew assembleDebug        | 打包所有渠道 测试包      |  
| Mac        | ./gradlew assembleGoogleDebug        | 打包google渠道 测试包      |  

