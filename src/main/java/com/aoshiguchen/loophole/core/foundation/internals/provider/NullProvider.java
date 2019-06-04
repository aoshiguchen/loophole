package com.aoshiguchen.loophole.core.foundation.internals.provider;

import com.aoshiguchen.loophole.core.dto.Ipv4;
import com.aoshiguchen.loophole.core.dto.Ipv4Segment;
import com.aoshiguchen.loophole.core.foundation.spi.provider.*;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public class NullProvider implements NetworkProvider,Ipv4Provider,DingProvider,RedisProvider {

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
  public boolean check(String host) {
    return false;
  }

  @Override
  public boolean check(String host, int port) {
    return false;
  }

  @Override
  public void ergodicPublicNetworkIp(Ipv4Segment ipv4Segment, Function<Ipv4, Boolean> filter, BiConsumer<Ipv4, Integer> progress, Consumer<Ipv4> consumer) {

  }

  @Override
  public void notice(String title, String msg) {

  }

  @Override
  public boolean check(Ipv4 ip) {
    return false;
  }

  @Override
  public boolean check(Ipv4 ip, int timeout) {
    return false;
  }

  @Override
  public boolean check(Ipv4 ip, int port, int timeout) {
    return false;
  }

  @Override
  public boolean check(String host, int port, int timeout) {
    return false;
  }

  @Override
  public String toString() {
    return "(NullProvider)";
  }
}
