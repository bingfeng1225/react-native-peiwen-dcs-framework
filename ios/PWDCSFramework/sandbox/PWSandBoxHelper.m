//
//  IWSandBoxHelper.m
//
//
//  Created by peiwen on 15/7/11.
//  Copyright (c) 2015年 PivoVR. All rights reserved.
//
#import "PWSandBoxHelper.h"

@implementation PWSandBoxHelper

+ (NSString *)homePath{
    return NSHomeDirectory();
}

+ (NSString *)appPath
{
    NSArray * paths = NSSearchPathForDirectoriesInDomains(NSApplicationDirectory, NSUserDomainMask, YES);
    return [paths objectAtIndex:0];
}

+ (NSString *)docPath
{
    NSArray * paths = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
    return [paths objectAtIndex:0];
}

+ (NSString *)libPath
{
    NSArray * paths = NSSearchPathForDirectoriesInDomains(NSLibraryDirectory, NSUserDomainMask, YES);
    return [paths objectAtIndex:0];
}

+ (NSString *)libPrefPath
{
    NSArray * paths = NSSearchPathForDirectoriesInDomains(NSLibraryDirectory, NSUserDomainMask, YES);
    return [[paths objectAtIndex:0] stringByAppendingFormat:@"/Preferences"];
}

+ (NSString *)cachesPath{
    NSArray *paths = NSSearchPathForDirectoriesInDomains(NSCachesDirectory, NSUserDomainMask, YES);
    return [paths objectAtIndex:0];
}

+ (NSString *)libCachePath
{
    NSArray * paths = NSSearchPathForDirectoriesInDomains(NSLibraryDirectory, NSUserDomainMask, YES);
    return [[paths objectAtIndex:0] stringByAppendingFormat:@"/Caches"];
}

+ (NSString *)tempPath {
    return [NSHomeDirectory() stringByAppendingFormat:@"/tmp"];
}

+ (NSString *)downloadPath{
    NSString *path = [[self libCachePath] stringByAppendingFormat:@"/download"];
    [self hasLive:path];
    return path;
}

+ (BOOL)hasLive:(NSString *)path
{
    if ( NO == [[NSFileManager defaultManager] fileExistsAtPath:path] )
    {
        return [[NSFileManager defaultManager] createDirectoryAtPath:path
                                         withIntermediateDirectories:YES
                                                          attributes:nil
                                                               error:NULL];
    }
    
    return YES;
}

+ (BOOL)fileExists:(NSString *)path{
    return  [[NSFileManager defaultManager] fileExistsAtPath:path];
}

+(BOOL)deleteFile:(NSString *)path{
    if ([self fileExists:path]) {
        NSFileManager* fileManager=[NSFileManager defaultManager];
        return  [fileManager removeItemAtPath:path error:nil];
    }
    return YES;
}

+(long long)fileSizeForPath:(NSString *)path
{
    long long fileSize = 0;
    if([self fileExists:path]){
        NSError *error = nil;
        NSDictionary *fileDict = [[NSFileManager defaultManager] attributesOfItemAtPath:path error:&error];
        if (!error && fileDict) {
            fileSize = [fileDict fileSize];
        }
    }
    return fileSize;
}

+ (long long)freeDiskSpace{
    NSDictionary *fattributes = [[NSFileManager defaultManager] attributesOfFileSystemForPath:NSHomeDirectory() error:nil];
    return [[fattributes objectForKey:NSFileSystemFreeSize] longLongValue];
}

+ (long long)freeSpace{
    NSString* path = [self docPath];
    NSFileManager* fileManager = [[NSFileManager alloc ] init];
    NSDictionary *fileSysAttributes = [fileManager attributesOfFileSystemForPath:path error:nil];
    NSNumber *freeSpace = [fileSysAttributes objectForKey:NSFileSystemFreeSize];
    return [freeSpace longLongValue];
}

+ (long long)totalSpace{
    NSString* path = [self docPath];
    NSFileManager* fileManager = [[NSFileManager alloc ] init];
    NSDictionary *fileSysAttributes = [fileManager attributesOfFileSystemForPath:path error:nil];
    NSNumber *totalSpace = [fileSysAttributes objectForKey:NSFileSystemSize];
    return [totalSpace longLongValue];
}


+ (BOOL)writeFile:(NSString *)path data:(NSData* )data length:(NSUInteger)length{
    NSOutputStream *outputStream = [NSOutputStream outputStreamToFileAtPath:path append:YES];
    [outputStream open];
    [outputStream write:[data bytes] maxLength:length];
    [outputStream close];
    outputStream = nil;
    return YES;
}

@end
