package com.aoshiguchen.loophole.core.foundation.internals.provider;

import com.aoshiguchen.loophole.core.dto.Ipv4;
import com.aoshiguchen.loophole.core.foundation.spi.provider.DingProvider;
import com.aoshiguchen.loophole.core.foundation.spi.provider.Ipv4Provider;
import com.aoshiguchen.loophole.core.foundation.spi.provider.NetworkProvider;
import com.aoshiguchen.loophole.core.foundation.spi.provider.Provider;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public class NullProvider implements NetworkProvider,Ipv4Provider,DingProvider {

  @Override
  public String getHostAddress() {
    return null;
  }

  @Override
  public String getHostName() {
    return null;
  }

  @Override
  public Class<? extends Provider> getType() {
    return null;
  }

  @Override
  public String getProperty(String name, String defaultValue) {
    return null;
  }

  @Override
  public void initialize() {

  }

  @Override
  public void ergodicPublicNetworkIp(String from, String to, Consumer<Ipv4> consumer) {

  }

  @Override
  public void notice(String msg) {

  }

  @Override
  public void ergodicPublicNetworkIp(String from, String to, BiConsumer<Ipv4, Integer> consumer) {

  }

  @Override
  public void ergodicPublicNetworkIp(String from, String to, Function<Ipv4, Boolean> filter, BiConsumer<Ipv4, Integer> progress, Consumer<Ipv4> consumer) {

  }

  @Override
  public String toString() {
    return "(NullProvider)";
  }
}
