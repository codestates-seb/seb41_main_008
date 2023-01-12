package com.nfteam.server.config.auth.dto;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;


public enum OAuthAttributes {
    GOOGLE("google",(attributes)->{
        MemberProfile memberProfile=new MemberProfile();
        memberProfile.setEmail((String) attributes.get("email"));
        return memberProfile;
    }),

    /*
    * naver, Kakao 들어갈 예정?
    * */

    KAKAO("Kakao",(attributes)->{

        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");


        MemberProfile memberProfile = new MemberProfile();
        memberProfile.setEmail((String) kakaoAccount.get("email"));
        return memberProfile;
    }),

    NAVER("Naver",(attributes)->{

        Map<String, Object> response = (Map<String, Object>) attributes.get("response");


        MemberProfile memberProfile = new MemberProfile();
        memberProfile.setEmail((String) response.get("email"));
        return memberProfile;
    });


    private final String registrationId;
    private final Function<Map<String,Object>,MemberProfile> of;

    //Function<T,R> T=입력 타입, R 리턴 타입
    //-> Map 형태의 데이터를 받아 MemberProfile 형태의 데이터로 변환


    OAuthAttributes(String registrationId,Function<Map<String,Object>, MemberProfile> of){
        this.registrationId=registrationId;
        this.of=of;
    }


    public static MemberProfile extract(String registrationId, Map<String, Object> attributes) {

        return Arrays.stream(values())
                .filter(provider->registrationId.equals(provider.registrationId))
                .findFirst() //registrationId와 일치하는 enum 데이터를 제공 (registarationId가 Kakao쪽이라면 카카오와 일치하는 데이터를
                //stream에서 가장 빠른 걸 찾아서 반환 )
                .orElseThrow(IllegalArgumentException::new)
                .of.apply(attributes);
        //Oauth RegisterId 가 memberProfile의 데이터와 일치하는지;
    }
}
