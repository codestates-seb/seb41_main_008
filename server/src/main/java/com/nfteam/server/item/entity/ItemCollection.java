package com.nfteam.server.item.entity;

import com.nfteam.server.common.audit.BaseEntity;
import com.nfteam.server.coin.entity.Coin;
import com.nfteam.server.dto.response.item.CollectionResponse;
import com.nfteam.server.dto.response.item.UserCollectionResponse;
import com.nfteam.server.member.entity.Member;
import lombok.Builder;
import lombok.Getter;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "item_collection")
public class ItemCollection extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "collection_id")
    private Long collectionId;

    // 해당 그룹(컬렉션) 소유자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    // 해당 그룹(컬렉션) 다루는 코인
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coin_id")
    private Coin coin;

    // 해당 아이템 그룹에 속한 아이템 리스트
    @OneToMany(mappedBy = "collection")
    private List<Item> itemList = new ArrayList<>();

    @Column(name = "col_name", length = 200, nullable = false)
    private String collectionName;

    @Column(name = "col_desc", length = 1000, nullable = false)
    private String description;

    @Column(name = "logo_img_name")
    private String logoImgName;

    @Column(name = "banner_img_name")
    private String bannerImgName;

    protected ItemCollection() {
    }

    public ItemCollection(Long collectionId) {
        this.collectionId = collectionId;
    }

    @Builder
    public ItemCollection(Long collectionId,
                          String collectionName,
                          String description,
                          String logoImgName,
                          String bannerImgName) {
        this.collectionId = collectionId;
        this.collectionName = collectionName;
        this.description = description;
        initImageInfo(logoImgName, bannerImgName);
    }

    private void initImageInfo(String logoImgName, String bannerImgName) {
        if (StringUtils.isEmpty(logoImgName)) {
            this.logoImgName = "col_default_logo";
        } else this.logoImgName = logoImgName;

        if (StringUtils.isEmpty(bannerImgName)) {
            this.bannerImgName = "col_default_banner";
        } else this.bannerImgName = bannerImgName;
    }

    public void update(ItemCollection collection) {
        updateName(collection.getCollectionName());
        updateDesc(collection.getDescription());
        updateLogoImg(collection.getLogoImgName());
        updateBannerImg(collection.getBannerImgName());
    }

    private void updateName(String name) {
        if (name != null) this.collectionName = name;
    }

    private void updateDesc(String description) {
        if (description != null) this.description = description;
    }

    private void updateLogoImg(String logoImgName) {
        if (logoImgName != null) this.logoImgName = logoImgName;
    }

    private void updateBannerImg(String bannerImgName) {
        if (bannerImgName != null) this.bannerImgName = bannerImgName;
    }

    public void assignMember(Member member) {
        this.member = member;
        member.getCollectionList().add(this);
    }

    public void assignCoin(Coin coin) {
        this.coin = coin;
    }

    public void addItem(Item item) {
        this.itemList.add(item);
        item.assignCollection(this);
    }

    public CollectionResponse toResponse() {
        return CollectionResponse.builder()
                .collectionId(collectionId)
                .collectionName(collectionName)
                .description(description)
                .logoImgName(logoImgName)
                .bannerImgName(bannerImgName)
                .createdDate(getCreatedDate())
                .ownerId(member.getMemberId())
                .ownerName(member.getNickname())
                .coinId(coin.getCoinId())
                .coinName(coin.getCoinName())
                .build();
    }

    public UserCollectionResponse toUserResponse() {
        return UserCollectionResponse.builder()
                .collectionId(collectionId)
                .collectionName(collectionName)
                .description(description)
                .logoImgName(logoImgName)
                .bannerImgName(bannerImgName)
                .createdDate(getCreatedDate())
                .coinId(coin.getCoinId())
                .coinName(coin.getCoinName())
                .build();
    }
}
