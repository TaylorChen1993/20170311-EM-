%����2����ά��̬����
MU1    = [1 2];
SIGMA1 = [1 0; 0 0.5];
MU2    = [-1 -1];
SIGMA2 = [1 0; 0 1];
%mvnrnd(MU1, SIGMA1, 1000)Ϊ������ֵΪMU1,Э�������ΪSIGMA1�ĵ���˹ģ�͵�1000�����������
%[A;B]��A���ϣ�B���¹��ɵľ���
%���´��뼴1000����һ������˹ģ�����ɵĵ��1000���ڶ�������˹ģ�����ɵĵ㣬ÿ�ж���һ����
X      = [mvnrnd(MU1, SIGMA1, 1000);mvnrnd(MU2, SIGMA2, 1000)];
[samplesCount,dimensionsCount] = size(X);
%������ļ���ʹ��fprintf()
fid = fopen('samplesData.txt','w');
fprintf(fid,'{\n');
for sampleID = 1:samplesCount
    fprintf(fid,'{%12.8f,%12.8f},\n',X(sampleID,1),X(sampleID,2));
end
fprintf(fid,'}\n');
fclose(fid);

%��ʾ
%x(:,1)��ʾx�ĵ�һ������Ԫ��
%scatter��X,Y,S,C����ɢ��ͼ,X��YΪ����������ɢ�����꣬SΪɢ���ֱ����C��ʾÿ�������ɫ����ʾ������
scatter(X(:,1),X(:,2),100,'.');

%GMMs ѧϰ,���Լ���ƴ���, �������Matlab�Դ���ѧϰ����
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
options = statset('Display','final');
obj = gmdistribution.fit(X,2,'Options',options);
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%��ʾѧϰ��ģ��
figure,h = ezmesh(@(x,y)pdf(obj,[x,y]),[-8 6], [-8 6]);

%��ʾѧϰ����
mu = obj.mu
sigma = obj.Sigma

