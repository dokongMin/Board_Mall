package com.dokong.board.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 1267973204L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUser user = new QUser("user");

    public final com.dokong.board.domain.baseentity.QBaseTimeEntity _super = new com.dokong.board.domain.baseentity.QBaseTimeEntity(this);

    public final QAddress address;

    public final ListPath<com.dokong.board.domain.board.BoardComment, com.dokong.board.domain.board.QBoardComment> boardComments = this.<com.dokong.board.domain.board.BoardComment, com.dokong.board.domain.board.QBoardComment>createList("boardComments", com.dokong.board.domain.board.BoardComment.class, com.dokong.board.domain.board.QBoardComment.class, PathInits.DIRECT2);

    public final ListPath<com.dokong.board.domain.board.BoardLike, com.dokong.board.domain.board.QBoardLike> boardLikes = this.<com.dokong.board.domain.board.BoardLike, com.dokong.board.domain.board.QBoardLike>createList("boardLikes", com.dokong.board.domain.board.BoardLike.class, com.dokong.board.domain.board.QBoardLike.class, PathInits.DIRECT2);

    public final ListPath<com.dokong.board.domain.board.Board, com.dokong.board.domain.board.QBoard> boards = this.<com.dokong.board.domain.board.Board, com.dokong.board.domain.board.QBoard>createList("boards", com.dokong.board.domain.board.Board.class, com.dokong.board.domain.board.QBoard.class, PathInits.DIRECT2);

    public final ListPath<CartProduct, QCartProduct> cartProducts = this.<CartProduct, QCartProduct>createList("cartProducts", CartProduct.class, QCartProduct.class, PathInits.DIRECT2);

    public final ListPath<Coupon, QCoupon> coupons = this.<Coupon, QCoupon>createList("coupons", Coupon.class, QCoupon.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final StringPath email = createString("email");

    public final StringPath gender = createString("gender");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath name = createString("name");

    public final ListPath<com.dokong.board.domain.order.Order, com.dokong.board.domain.order.QOrder> orders = this.<com.dokong.board.domain.order.Order, com.dokong.board.domain.order.QOrder>createList("orders", com.dokong.board.domain.order.Order.class, com.dokong.board.domain.order.QOrder.class, PathInits.DIRECT2);

    public final StringPath password = createString("password");

    public final StringPath phoneNumber = createString("phoneNumber");

    public final StringPath username = createString("username");

    public QUser(String variable) {
        this(User.class, forVariable(variable), INITS);
    }

    public QUser(Path<? extends User> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUser(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUser(PathMetadata metadata, PathInits inits) {
        this(User.class, metadata, inits);
    }

    public QUser(Class<? extends User> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.address = inits.isInitialized("address") ? new QAddress(forProperty("address")) : null;
    }

}

