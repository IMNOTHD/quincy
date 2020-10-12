package com.piggy.quincy.utils.picture;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.32.1)",
    comments = "Source: PictureSpider.proto")
public final class PictureSpiderGrpc {

  private PictureSpiderGrpc() {}

  public static final String SERVICE_NAME = "proto.v1.PictureSpider";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<PictureSpiderService.PictureUrlRequest,
      PictureSpiderService.PictureUrlResponse> getPictureUrlMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "PictureUrl",
      requestType = PictureSpiderService.PictureUrlRequest.class,
      responseType = PictureSpiderService.PictureUrlResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<PictureSpiderService.PictureUrlRequest,
      PictureSpiderService.PictureUrlResponse> getPictureUrlMethod() {
    io.grpc.MethodDescriptor<PictureSpiderService.PictureUrlRequest, PictureSpiderService.PictureUrlResponse> getPictureUrlMethod;
    if ((getPictureUrlMethod = PictureSpiderGrpc.getPictureUrlMethod) == null) {
      synchronized (PictureSpiderGrpc.class) {
        if ((getPictureUrlMethod = PictureSpiderGrpc.getPictureUrlMethod) == null) {
          PictureSpiderGrpc.getPictureUrlMethod = getPictureUrlMethod =
              io.grpc.MethodDescriptor.<PictureSpiderService.PictureUrlRequest, PictureSpiderService.PictureUrlResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "PictureUrl"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  PictureSpiderService.PictureUrlRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  PictureSpiderService.PictureUrlResponse.getDefaultInstance()))
              .setSchemaDescriptor(new PictureSpiderMethodDescriptorSupplier("PictureUrl"))
              .build();
        }
      }
    }
    return getPictureUrlMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static PictureSpiderStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PictureSpiderStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PictureSpiderStub>() {
        @Override
        public PictureSpiderStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PictureSpiderStub(channel, callOptions);
        }
      };
    return PictureSpiderStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static PictureSpiderBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PictureSpiderBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PictureSpiderBlockingStub>() {
        @Override
        public PictureSpiderBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PictureSpiderBlockingStub(channel, callOptions);
        }
      };
    return PictureSpiderBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static PictureSpiderFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<PictureSpiderFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<PictureSpiderFutureStub>() {
        @Override
        public PictureSpiderFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new PictureSpiderFutureStub(channel, callOptions);
        }
      };
    return PictureSpiderFutureStub.newStub(factory, channel);
  }

  /**
   */
  public static abstract class PictureSpiderImplBase implements io.grpc.BindableService {

    /**
     */
    public void pictureUrl(PictureSpiderService.PictureUrlRequest request,
                           io.grpc.stub.StreamObserver<PictureSpiderService.PictureUrlResponse> responseObserver) {
      asyncUnimplementedUnaryCall(getPictureUrlMethod(), responseObserver);
    }

    @Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getPictureUrlMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                PictureSpiderService.PictureUrlRequest,
                PictureSpiderService.PictureUrlResponse>(
                  this, METHODID_PICTURE_URL)))
          .build();
    }
  }

  /**
   */
  public static final class PictureSpiderStub extends io.grpc.stub.AbstractAsyncStub<PictureSpiderStub> {
    private PictureSpiderStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected PictureSpiderStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PictureSpiderStub(channel, callOptions);
    }

    /**
     */
    public void pictureUrl(PictureSpiderService.PictureUrlRequest request,
                           io.grpc.stub.StreamObserver<PictureSpiderService.PictureUrlResponse> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getPictureUrlMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class PictureSpiderBlockingStub extends io.grpc.stub.AbstractBlockingStub<PictureSpiderBlockingStub> {
    private PictureSpiderBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected PictureSpiderBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PictureSpiderBlockingStub(channel, callOptions);
    }

    /**
     */
    public PictureSpiderService.PictureUrlResponse pictureUrl(PictureSpiderService.PictureUrlRequest request) {
      return blockingUnaryCall(
          getChannel(), getPictureUrlMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class PictureSpiderFutureStub extends io.grpc.stub.AbstractFutureStub<PictureSpiderFutureStub> {
    private PictureSpiderFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @Override
    protected PictureSpiderFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new PictureSpiderFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<PictureSpiderService.PictureUrlResponse> pictureUrl(
        PictureSpiderService.PictureUrlRequest request) {
      return futureUnaryCall(
          getChannel().newCall(getPictureUrlMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_PICTURE_URL = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final PictureSpiderImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(PictureSpiderImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_PICTURE_URL:
          serviceImpl.pictureUrl((PictureSpiderService.PictureUrlRequest) request,
              (io.grpc.stub.StreamObserver<PictureSpiderService.PictureUrlResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @Override
    @SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static abstract class PictureSpiderBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    PictureSpiderBaseDescriptorSupplier() {}

    @Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return PictureSpiderService.getDescriptor();
    }

    @Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("PictureSpider");
    }
  }

  private static final class PictureSpiderFileDescriptorSupplier
      extends PictureSpiderBaseDescriptorSupplier {
    PictureSpiderFileDescriptorSupplier() {}
  }

  private static final class PictureSpiderMethodDescriptorSupplier
      extends PictureSpiderBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final String methodName;

    PictureSpiderMethodDescriptorSupplier(String methodName) {
      this.methodName = methodName;
    }

    @Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (PictureSpiderGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new PictureSpiderFileDescriptorSupplier())
              .addMethod(getPictureUrlMethod())
              .build();
        }
      }
    }
    return result;
  }
}
